package com.springboot.hotelmanagement.dto.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PriceResponseDTO {
	private Long id;
	private String name;
	private LocalDate startDate;
	private LocalDate endDate;
	private Double price;
}
