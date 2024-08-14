package com.springboot.hotelmanagement.service;

import com.springboot.hotelmanagement.dto.request.GuestCreateRequestDTO;
import com.springboot.hotelmanagement.dto.request.GuestUpdateRequestDTO;
import com.springboot.hotelmanagement.dto.response.GuestResponseDTO;
import com.springboot.hotelmanagement.entity.Guest;
import com.springboot.hotelmanagement.entity.Reservation;
import com.springboot.hotelmanagement.repository.GuestRepository;
import com.springboot.hotelmanagement.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GuestService {

	private final GuestRepository guestRepository;
	private final ModelMapper modelMapper;
	private final ReservationRepository reservationRepository;

	public void createGuest(GuestCreateRequestDTO guestRequestDTO) {
		if(guestRepository.existsByTcPassportNumber(guestRequestDTO.getTcPassportNumber())) {
			throw new RuntimeException("A customer with the same T.C. / Passport number already exists.");
		}

		Guest guest = modelMapper.map(guestRequestDTO, Guest.class);
		guestRepository.save(guest);
	}

	public GuestResponseDTO getPrice(Long id) {
		Guest guest = guestRepository.findById(id).orElseThrow(() -> new RuntimeException("Guest not found with ID: " + id));
		return modelMapper.map(guest, GuestResponseDTO.class);
	}

	public List<GuestResponseDTO> getAllGuests() {
		List<Guest> guests = guestRepository.findAll();
		return guests.stream().map(guest -> modelMapper.map(guest, GuestResponseDTO.class)).toList();
	}

	public void deleteGuest(Long id) {
		Guest guest = guestRepository.findById(id).orElseThrow(() -> new RuntimeException("Guest not found with ID: " + id));

		List<Reservation> reservations = reservationRepository.findAllByGuests_Id(id);

		for (Reservation reservation : reservations) {
			if (reservation.getGuests().size() == 1) {
				throw new IllegalArgumentException("Reservation with ID " + reservation.getId() + " cannot have no guests. Deletion failed.");
			}
			reservation.getGuests().remove(guest);
			reservationRepository.save(reservation);
		}

		guestRepository.delete(guest);
	}

	public void updateGuest(Long id, GuestUpdateRequestDTO guestUpdateRequestDTO) {
		Guest guest = guestRepository.findById(id).orElseThrow(() -> new RuntimeException("Guest not found with ID: " + id));
		modelMapper.map(guestUpdateRequestDTO, guest);
		guestRepository.save(guest);
		modelMapper.map(guest, GuestResponseDTO.class);
	}
}
