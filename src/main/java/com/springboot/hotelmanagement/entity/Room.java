package com.springboot.hotelmanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String roomNumber;

	@Column
	private String description;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "room_price",
			joinColumns = @JoinColumn(name = "room_id"),
			inverseJoinColumns = @JoinColumn(name = "price_id")
	)
	private Set<Price> prices = new HashSet<>();
}
