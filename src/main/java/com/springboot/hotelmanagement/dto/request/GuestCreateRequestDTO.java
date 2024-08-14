package com.springboot.hotelmanagement.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuestCreateRequestDTO {
	@NotNull(message = "Name cannot be null")
	private String name;

	@NotNull(message = "Surname cannot be null")
	private String surname;

	@NotNull(message = "T.C. / Passport Number cannot be null")
	private String tcPassportNumber;
	private String email;
	private String phone;
	private String country;
}
