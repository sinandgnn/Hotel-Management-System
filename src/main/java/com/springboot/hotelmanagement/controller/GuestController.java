package com.springboot.hotelmanagement.controller;

import com.springboot.hotelmanagement.dto.request.GuestCreateRequestDTO;
import com.springboot.hotelmanagement.dto.request.GuestUpdateRequestDTO;
import com.springboot.hotelmanagement.dto.response.GuestResponseDTO;
import com.springboot.hotelmanagement.service.GuestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guest")
@RequiredArgsConstructor
public class GuestController {
	private final GuestService guestService;

	@PostMapping("/create")
	public ResponseEntity<String> createGuest(@Valid @RequestBody GuestCreateRequestDTO guestRequestDTO) {
		try {
			guestService.createGuest(guestRequestDTO);
			return new ResponseEntity<>("Guest created successfully.", HttpStatus.CREATED);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/get")
	public ResponseEntity<?> getGuest(@RequestParam Long id) {
		try {
			GuestResponseDTO guestResponse = guestService.getPrice(id);
			return new ResponseEntity<>(guestResponse, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/getAll")
	public ResponseEntity<?> getAllGuests() {
		List<GuestResponseDTO> guests = guestService.getAllGuests();
		return new ResponseEntity<>(guests, HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteGuest(@RequestParam Long id) {
		try {
			guestService.deleteGuest(id);
			return new ResponseEntity<>("Guest deleted successfully.", HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/update")
	public ResponseEntity<String> updateGuest(@RequestParam Long id, @RequestBody GuestUpdateRequestDTO guestRequestDTO) {
		try {
			guestService.updateGuest(id, guestRequestDTO);
			return new ResponseEntity<>("Guest updated successfully.", HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}
