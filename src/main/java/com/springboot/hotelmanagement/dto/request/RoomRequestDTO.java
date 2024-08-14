package com.springboot.hotelmanagement.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RoomRequestDTO {

	@NotNull(message = "Room number can not be null")
	private String roomNumber;

	private String description;
	private Set<Long> priceIds;
}