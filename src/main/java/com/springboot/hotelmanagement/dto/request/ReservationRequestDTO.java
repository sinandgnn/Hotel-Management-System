package com.springboot.hotelmanagement.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class ReservationRequestDTO {

	@NotNull(message = "Guest IDs must not be null")
	private Set<Long> guestIds;

	@NotNull(message = "Room ID must not be null")
	private Long roomId;

	@NotNull(message = "Check-in date must not be null")
	private LocalDate checkinDate;

	@NotNull(message = "Checkout date must not be null")
	private LocalDate checkoutDate;

	@NotNull(message = "Total price must not be null")
	private Long totalPrice;
}
