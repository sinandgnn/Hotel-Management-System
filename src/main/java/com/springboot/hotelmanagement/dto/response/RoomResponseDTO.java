package com.springboot.hotelmanagement.dto.response;

import lombok.Data;

import java.util.Set;


@Data
public class RoomResponseDTO {
	private Long id;
	private String roomNumber;
	private String description;
	private Set<PriceResponseDTO> prices;
}
