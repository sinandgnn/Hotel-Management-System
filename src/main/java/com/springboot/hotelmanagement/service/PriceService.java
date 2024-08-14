package com.springboot.hotelmanagement.service;

import com.springboot.hotelmanagement.dto.request.PriceCreateRequestDTO;
import com.springboot.hotelmanagement.dto.request.PriceUpdateRequestDTO;
import com.springboot.hotelmanagement.dto.response.PriceResponseDTO;
import com.springboot.hotelmanagement.entity.Price;
import com.springboot.hotelmanagement.repository.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PriceService {
	private final PriceRepository priceRepository;
	private final ModelMapper modelMapper;

	public void createPrice(PriceCreateRequestDTO priceRequestDTO) {
		Price price = modelMapper.map(priceRequestDTO, Price.class);
		priceRepository.save(price);
	}

	public PriceResponseDTO getPrice(Long id) {
		Price price = priceRepository.findById(id).orElseThrow(() -> new RuntimeException("Price not found with ID: " + id));
		return modelMapper.map(price, PriceResponseDTO.class);
	}

	public List<PriceResponseDTO> getAllPrices() {
		List<Price> prices = priceRepository.findAll();
		return prices.stream()
				.map(price -> modelMapper.map(price, PriceResponseDTO.class))
				.toList();
	}

	public void deletePrice(Long id) {
		Price price = priceRepository.findById(id).orElseThrow(() -> new RuntimeException("Price not found with id: " + id));

		if (!price.getRooms().isEmpty()) {
			String roomIds = price.getRooms().stream()
					.map(room -> String.valueOf(room.getId()))
					.collect(Collectors.joining(", "));
			throw new IllegalStateException("Price cannot be deleted because it is associated with the following rooms: " + roomIds);
		}

		priceRepository.delete(price);
	}

	public void updatePrice(Long id, PriceUpdateRequestDTO priceUpdateRequestDTO) {
		Price price = priceRepository.findById(id).orElseThrow(() -> new RuntimeException("Price not found with id: " + id));

		modelMapper.map(priceUpdateRequestDTO, price);
		priceRepository.save(price);
		modelMapper.map(price, PriceResponseDTO.class);
	}
}