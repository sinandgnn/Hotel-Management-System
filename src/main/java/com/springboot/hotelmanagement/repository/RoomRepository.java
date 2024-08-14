package com.springboot.hotelmanagement.repository;

import com.springboot.hotelmanagement.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
	boolean existsByRoomNumber(String roomNumber);
}
