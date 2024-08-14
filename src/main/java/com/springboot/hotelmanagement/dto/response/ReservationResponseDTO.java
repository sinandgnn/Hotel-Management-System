package com.springboot.hotelmanagement.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class ReservationResponseDTO {
	private Long id;
	private LocalDate checkinDate;
	private LocalDate checkoutDate;
	private Double totalPrice;
	private RoomInfoResponseDTO room;
	private String reservationStatus;
	private Set<GuestInfoResponseDTO> guests;
}
