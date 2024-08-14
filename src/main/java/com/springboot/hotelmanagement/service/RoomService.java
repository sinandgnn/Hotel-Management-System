package com.springboot.hotelmanagement.service;

import com.springboot.hotelmanagement.dto.request.RoomRequestDTO;
import com.springboot.hotelmanagement.dto.response.RoomResponseDTO;
import com.springboot.hotelmanagement.entity.Price;
import com.springboot.hotelmanagement.entity.Room;
import com.springboot.hotelmanagement.repository.PriceRepository;
import com.springboot.hotelmanagement.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {
	private final RoomRepository roomRepository;
	private final PriceRepository priceRepository;
	private final ModelMapper modelMapper;

	public void createRoom(RoomRequestDTO roomRequestDTO) {
		if (roomRepository.existsByRoomNumber(roomRequestDTO.getRoomNumber())) {
			throw new RuntimeException("Room with number " + roomRequestDTO.getRoomNumber() + " already exists.");
		}

		if (roomRequestDTO.getPriceIds() == null) {
			roomRequestDTO.setPriceIds(new HashSet<>());
		}

		Room room = modelMapper.map(roomRequestDTO, Room.class);

		if (!roomRequestDTO.getPriceIds().isEmpty()) {
			HashSet<Price> prices = new HashSet<>(priceRepository.findAllById(roomRequestDTO.getPriceIds()));
			room.setPrices(prices);
		}

		roomRepository.save(room);
	}

	public RoomResponseDTO getRoom(Long id) {
		Room room = roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Room not found with id: " + id));
		return modelMapper.map(room, RoomResponseDTO.class);
	}

	public List<RoomResponseDTO> getAllRooms() {
		List<Room> rooms = roomRepository.findAll();
		return rooms.stream()
				.map(room -> modelMapper.map(room, RoomResponseDTO.class))
				.toList();
	}

	public void deleteRoom(Long id) {
		Room room = roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Room not found with id: " + id));

		roomRepository.delete(room);
	}

	public void updateRoom(Long id, RoomRequestDTO roomRequestDTO) {
		Room room = roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Room not found with id: " + id));

		if(roomRequestDTO.getRoomNumber() != null && roomRepository.existsByRoomNumber(roomRequestDTO.getRoomNumber())) {
			throw new RuntimeException("Room with number " + roomRequestDTO.getRoomNumber() + " already exists.");
		}

		if(roomRequestDTO.getPriceIds() != null) {
			HashSet<Price> prices = new HashSet<>(priceRepository.findAllById(roomRequestDTO.getPriceIds()));
			room.setPrices(prices);
		}

		modelMapper.map(roomRequestDTO, room);
		roomRepository.save(room);
		modelMapper.map(room, RoomResponseDTO.class);
	}

	public void addPriceToRoom(Long roomId, Long priceId) {
		Room room = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found with id: " + roomId));
		Price price = priceRepository.findById(priceId).orElseThrow(() -> new RuntimeException("Price not found with id: " + priceId));

		if (room.getPrices().contains(price)) {
			throw new RuntimeException("Price already exists in the specified Room");
		}

		room.getPrices().add(price);
		price.getRooms().add(room);

		roomRepository.save(room);
		priceRepository.save(price);
	}

	public void removePriceFromRoom(Long roomId, Long priceId) {
		Room room = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found with id: " + roomId));
		Price price = priceRepository.findById(priceId).orElseThrow(() -> new RuntimeException("Price not found with id: " + priceId));

		if (!room.getPrices().contains(price)) {
			throw new RuntimeException("Price does not exists in the specified Room");
		}

		room.getPrices().remove(price);
		price.getRooms().remove(room);

		roomRepository.save(room);
		priceRepository.save(price);
	}
}
