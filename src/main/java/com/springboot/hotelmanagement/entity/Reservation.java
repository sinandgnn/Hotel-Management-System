package com.springboot.hotelmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "reservation_guests",
			joinColumns = @JoinColumn(name = "reservation_id"),
			inverseJoinColumns = @JoinColumn(name = "guest_id")
	)
	private Set<Guest> guests;

	@ManyToOne
	@JoinColumn
	private Room room;

	@Column(nullable = false)
	private LocalDate checkinDate;

	@Column(nullable = false)
	private LocalDate checkoutDate;

	@Column(nullable = false)
	private Double totalPrice;

	@Enumerated(EnumType.STRING)
	@Column
	private ReservationStatus reservationStatus;

	public enum ReservationStatus {
		PRE_BOOKING,        // Ön rezervasyon
		CONFIRMED_BOOKING,  // Kesin rezervasyon
		CURRENTLY_STAYING,  // Şu an otelde
		CHECKED_OUT         // Çıkış yapıldı
	}

}
