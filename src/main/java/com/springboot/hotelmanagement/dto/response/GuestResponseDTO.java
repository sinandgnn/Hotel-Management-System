package com.springboot.hotelmanagement.dto.response;

import lombok.Data;

@Data
public class GuestResponseDTO {
	private Long id;
	private String name;
	private String surname;
	private String tcPassportNumber;
	private String email;
	private String phone;
	private String country;
}
