package com.springboot.hotelmanagement.service;

import com.springboot.hotelmanagement.dto.request.ReservationRequestDTO;
import com.springboot.hotelmanagement.dto.response.ReservationResponseDTO;
import com.springboot.hotelmanagement.entity.Guest;
import com.springboot.hotelmanagement.entity.Price;
import com.springboot.hotelmanagement.entity.Reservation;
import com.springboot.hotelmanagement.entity.Room;
import com.springboot.hotelmanagement.repository.GuestRepository;
import com.springboot.hotelmanagement.repository.ReservationRepository;
import com.springboot.hotelmanagement.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ReservationService {
	private final ReservationRepository reservationRepository;
	private final ModelMapper modelMapper;
	private final GuestRepository guestRepository;
	private final RoomRepository roomRepository;


	public void createReservation(ReservationRequestDTO reservationRequestDTO) {
		HashSet<Guest> guests = new HashSet<>(guestRepository.findAllById(reservationRequestDTO.getGuestIds()));
		if (guests.isEmpty()) {
			throw new RuntimeException("At least one Guest ID must be provided.");
		}

		Room room = roomRepository.findById(reservationRequestDTO.getRoomId()).
				orElseThrow(() -> new RuntimeException("Room not found with ID: " + reservationRequestDTO.getRoomId()));

		LocalDate checkinDate = reservationRequestDTO.getCheckinDate();
		LocalDate checkoutDate = reservationRequestDTO.getCheckoutDate();

		if (checkinDate.isAfter(checkoutDate) || checkinDate.isEqual(checkoutDate)) {
			throw new RuntimeException("Check-in date must be before checkout date.");
		}

		List<Reservation> conflictingReservations = reservationRepository.findConflictingReservations(room.getId(), checkinDate, checkoutDate);

		if (!conflictingReservations.isEmpty()) {
			throw new RuntimeException("Conflicting reservations found. Reservation ID: " + conflictingReservations.get(0).getId());
		}

		Reservation reservation = Reservation.builder().guests(guests).room(room).checkinDate(checkinDate).checkoutDate(checkoutDate).build();
		reservation.setTotalPrice(calculateTotalPrice(checkinDate, checkoutDate, room.getPrices()));
		reservation.setReservationStatus(Reservation.ReservationStatus.PRE_BOOKING);

		reservationRepository.save(reservation);
	}

	public ReservationResponseDTO getReservation(Long id) {
		Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new RuntimeException("Reservation not found with ID: " + id));
		return modelMapper.map(reservation, ReservationResponseDTO.class);
	}

	public List<ReservationResponseDTO> getAllReservations() {
		List<Reservation> reservations = reservationRepository.findAll();
		return reservations.stream()
				.map(reservation -> modelMapper.map(reservation, ReservationResponseDTO.class))
				.toList();
	}

	public void deleteReservation(Long id) {
		Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new RuntimeException("Reservation not found with ID: " + id));
		reservationRepository.delete(reservation);
	}

	public void updateReservation(Long id, ReservationRequestDTO reservationRequestDTO) {
		Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new RuntimeException("Reservation not found with ID: " + id));

		LocalDate checkinDate = reservationRequestDTO.getCheckinDate();
		LocalDate checkoutDate = reservationRequestDTO.getCheckoutDate();

		if ((checkinDate != null && checkoutDate != null && checkinDate.isAfter(checkoutDate))
				|| (checkinDate != null && checkoutDate == null && checkinDate.isAfter(reservation.getCheckoutDate()))
				|| (checkinDate == null && checkoutDate != null && reservation.getCheckinDate().isAfter(checkoutDate))) {
			throw new IllegalArgumentException("Check-in date must be before checkout date.");
		}

		modelMapper.map(reservationRequestDTO, reservation);
		modelMapper.map(reservation, ReservationResponseDTO.class);

		if (reservationRequestDTO.getTotalPrice() == null) {
			reservation.setTotalPrice(calculateTotalPrice(reservation.getCheckinDate(), reservation.getCheckoutDate(), reservation.getRoom().getPrices()));
		}

		reservationRepository.save(reservation);
	}

	public void addGuestToReservation(Long reservationId, Long guestId) {
		Reservation reservation = reservationRepository.findById(reservationId).
				orElseThrow(() -> new RuntimeException("Reservation not found with ID: " + reservationId));
		Guest guest = guestRepository.findById(guestId).orElseThrow(() -> new RuntimeException("Guest not found with ID: " + guestId));

		if (reservation.getGuests().contains(guest)) {
			throw new IllegalArgumentException("This guest is already in the reservation.");
		}

		reservation.getGuests().add(guest);
		reservationRepository.save(reservation);
	}

	public void removeGuestFromReservation(Long reservationId, Long guestId) {
		Reservation reservation = reservationRepository.findById(reservationId).
				orElseThrow(() -> new RuntimeException("Reservation not found with ID: " + reservationId));
		Guest guest = guestRepository.findById(guestId).orElseThrow(() -> new RuntimeException("Guest not found with ID: " + guestId));

		if (!reservation.getGuests().contains(guest)) {
			throw new RuntimeException("This guest is not in the reservation.");
		}

		if (reservation.getGuests().size() == 1) {
			throw new IllegalArgumentException("A reservation must have at least one guest. Cannot remove the last guest.");
		}

		reservation.getGuests().remove(guest);
		reservationRepository.save(reservation);
	}

	public void updateStatus(Long id, String status) {
		Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new RuntimeException("Reservation not found with ID: " + id));
		Reservation.ReservationStatus reservationStatus;

		try {
			reservationStatus = Reservation.ReservationStatus.valueOf(status);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid reservation status: " + status);
		}

		reservation.setReservationStatus(reservationStatus);
		reservationRepository.save(reservation);
	}

	public Double calculateTotalPrice(LocalDate checkinDate, LocalDate checkoutDate, Set<Price> roomPrices) {
		double totalPrice = 0.0;

		LocalDate currentDate = checkinDate;
		while (!currentDate.isAfter(checkoutDate.minusDays(1))) {
			double maxPriceForDay = 0;
			boolean priceFoundForDay = false;

			for (Price price : roomPrices) {
				if ((price.getStartDate().isBefore(currentDate) || price.getStartDate().isEqual(currentDate)) &&
						(price.getEndDate().isAfter(currentDate) || price.getEndDate().isEqual(currentDate))) {
					maxPriceForDay = Math.max(maxPriceForDay, price.getPrice());
					priceFoundForDay = true;
				}
			}

			if (!priceFoundForDay) {
				throw new RuntimeException("No valid price found for the date: " + currentDate);
			}

			totalPrice += maxPriceForDay;
			currentDate = currentDate.plusDays(1);
		}

		return totalPrice;
	}
}

