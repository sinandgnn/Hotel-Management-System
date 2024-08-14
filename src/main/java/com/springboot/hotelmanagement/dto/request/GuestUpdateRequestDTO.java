package com.springboot.hotelmanagement.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuestUpdateRequestDTO {
	private String name;
	private String surname;
	private String email;
	private String phone;
	private String country;
}
