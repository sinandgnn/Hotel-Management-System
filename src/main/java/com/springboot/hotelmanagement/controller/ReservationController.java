package com.springboot.hotelmanagement.controller;

import com.springboot.hotelmanagement.dto.request.ReservationRequestDTO;
import com.springboot.hotelmanagement.dto.response.ReservationResponseDTO;
import com.springboot.hotelmanagement.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservation")
@RequiredArgsConstructor
public class ReservationController {
	private final ReservationService reservationService;

	@PostMapping("/create")
	public ResponseEntity<String> createReservation(@Valid @RequestBody ReservationRequestDTO reservationRequestDTO) {
		try {
			reservationService.createReservation(reservationRequestDTO);
			return new ResponseEntity<>("Reservation created successfully.", HttpStatus.CREATED);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/get")
	public ResponseEntity<?> getReservation(@RequestParam Long id) {
		try {
			ReservationResponseDTO reservation = reservationService.getReservation(id);
			return new ResponseEntity<>(reservation, HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<ReservationResponseDTO>> getAllReservations() {
		List<ReservationResponseDTO> reservations = reservationService.getAllReservations();
		return new ResponseEntity<>(reservations, HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteReservation(@RequestParam Long id) {
		try {
			reservationService.deleteReservation(id);
			return new ResponseEntity<>("Reservation deleted successfully.", HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/update")
	public ResponseEntity<String> updateReservation(@RequestParam Long id, @RequestBody ReservationRequestDTO reservationRequestDTO) {
		try {
			reservationService.updateReservation(id, reservationRequestDTO);
			return new ResponseEntity<>("Reservation updated successfully.", HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/addGuest")
	public ResponseEntity<String> addGuestToReservation(@RequestParam Long reservationId, @RequestParam Long guestId) {
		try {
			reservationService.addGuestToReservation(reservationId, guestId);
			return new ResponseEntity<>("Guest added to reservation successfully.", HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/removeGuest")
	public ResponseEntity<String> removeGuestFromReservation(@RequestParam Long reservationId, @RequestParam Long guestId) {
		try {
			reservationService.removeGuestFromReservation(reservationId, guestId);
			return new ResponseEntity<>("Guest removed from reservation successfully.", HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/updateStatus")
	public ResponseEntity<String> updateStatus(@RequestParam Long id, @RequestParam String status) {
		try {
			reservationService.updateStatus(id, status);
			return new ResponseEntity<>("Reservation status updated successfully.", HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}
