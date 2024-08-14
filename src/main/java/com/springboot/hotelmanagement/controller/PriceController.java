package com.springboot.hotelmanagement.controller;

import com.springboot.hotelmanagement.dto.request.PriceCreateRequestDTO;
import com.springboot.hotelmanagement.dto.request.PriceUpdateRequestDTO;
import com.springboot.hotelmanagement.dto.response.PriceResponseDTO;
import com.springboot.hotelmanagement.service.PriceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/price")
@RequiredArgsConstructor
public class PriceController {
	private final PriceService priceService;

	@PostMapping("/create")
	public ResponseEntity<String> createPrice(@Valid @RequestBody PriceCreateRequestDTO priceRequestDTO) {
		try {
			priceService.createPrice(priceRequestDTO);
			return new ResponseEntity<>("Price created successfully.", HttpStatus.CREATED);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/get")
	public ResponseEntity<?> getPrice(@RequestParam Long id) {
		try {
			PriceResponseDTO priceResponse = priceService.getPrice(id);
			return new ResponseEntity<>(priceResponse, HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<PriceResponseDTO>> getAllPrices() {
		List<PriceResponseDTO> prices = priceService.getAllPrices();
		return new ResponseEntity<>(prices, HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<String> deletePrice(@RequestParam Long id) {
		try {
			priceService.deletePrice(id);
			return new ResponseEntity<>("Price deleted successfully.", HttpStatus.OK);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/update")
	public ResponseEntity<String> updatePrice(@RequestParam Long id, @RequestBody PriceUpdateRequestDTO priceRequestDTO) {
		try {
			priceService.updatePrice(id, priceRequestDTO);
			return new ResponseEntity<>("Price updated successfully.", HttpStatus.OK);
		} catch (IllegalStateException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}
