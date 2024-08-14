package com.springboot.hotelmanagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table
public class Price {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private LocalDate startDate;

	@Column(nullable = false)
	private LocalDate endDate;

	@Column(nullable = false)
	private Double price;

	@ManyToMany(mappedBy = "prices", fetch = FetchType.EAGER)
	@JsonBackReference
	private Set<Room> rooms;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Price price1 = (Price) o;
		return Objects.equals(id, price1.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
