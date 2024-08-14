package com.springboot.hotelmanagement.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table
public class Guest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String surname;

	@Column(unique = true, nullable = false)
	private String tcPassportNumber;

	@Column
	private String email;

	@Column
	private String phone;

	@Column
	private String country;
}
