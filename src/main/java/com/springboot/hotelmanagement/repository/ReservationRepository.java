package com.springboot.hotelmanagement.repository;

import com.springboot.hotelmanagement.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	List<Reservation> findAllByGuests_Id(Long id);

	@Query("SELECT r FROM Reservation r WHERE r.room.id = :roomId AND " +
			"((r.checkinDate <= :checkoutDate AND r.checkoutDate >= :checkinDate))")
	List<Reservation> findConflictingReservations(@Param("roomId") Long roomId,
	                                              @Param("checkinDate") LocalDate checkinDate,
	                                              @Param("checkoutDate") LocalDate checkoutDate);

}
