package com.springboot.hotelmanagement.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PriceUpdateRequestDTO {
	private String name;
	private LocalDate startDate;
	private LocalDate endDate;
	private Double price;
}
