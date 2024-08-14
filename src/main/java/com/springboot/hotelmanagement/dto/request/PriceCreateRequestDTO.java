package com.springboot.hotelmanagement.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PriceCreateRequestDTO {
	@NotNull(message = "Name cannot be null")
	private String name;

	@NotNull(message = "Start date cannot be null")
	private LocalDate startDate;

	@NotNull(message = "End date cannot be null")
	private LocalDate endDate;

	@NotNull(message = "Price cannot be null")
	private Double price;
}
