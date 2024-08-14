package com.springboot.hotelmanagement.controller;

import com.springboot.hotelmanagement.dto.request.RoomRequestDTO;
import com.springboot.hotelmanagement.dto.response.RoomResponseDTO;
import com.springboot.hotelmanagement.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/room")
@RequiredArgsConstructor
public class RoomController {
	private final RoomService roomService;

	@PostMapping("/create")
	public ResponseEntity<String> createRoom(@Valid @RequestBody RoomRequestDTO roomRequestDTO) {
		try {
			roomService.createRoom(roomRequestDTO);
			return new ResponseEntity<>("Room created successfully.", HttpStatus.CREATED);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/get")
	public ResponseEntity<?> getRoom(@RequestParam Long id) {
		try {
			RoomResponseDTO roomResponse = roomService.getRoom(id);
			return new ResponseEntity<>(roomResponse, HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<RoomResponseDTO>> getAllRooms() {
		List<RoomResponseDTO> rooms = roomService.getAllRooms();
		return new ResponseEntity<>(rooms, HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteRoom(@RequestParam Long id) {
		try {
			roomService.deleteRoom(id);
			return new ResponseEntity<>("Room deleted successfully.", HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/update")
	public ResponseEntity<String> updateRoom(@RequestParam Long id, @RequestBody RoomRequestDTO roomRequestDTO) {
		try {
			roomService.updateRoom(id,roomRequestDTO);
			return new ResponseEntity<>("Room updated successfully.", HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/addPrice")
	public ResponseEntity<String> addPrice(@RequestParam Long roomId, @RequestParam Long priceId) {
		try {
			roomService.addPriceToRoom(roomId, priceId);
			return new ResponseEntity<>("Price added successfully.", HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/deletePrice")
	public ResponseEntity<String> deletePrice(@RequestParam Long roomId, @RequestParam Long priceId) {
		try {
			roomService.removePriceFromRoom(roomId, priceId);
			return new ResponseEntity<>("Price removed successfully.", HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
