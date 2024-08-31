package com.saraWoldegiorgis.AbyssiniaHotelBookingApp.repositories;

import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

//    We might have different types of rooms for a typical room like 3 different single rooms with
//    different styles & prices. But here we just need unique room types available in the hotel

    @Query("SELECT DISTINCT r.roomType FROM Room r")
    List<String> findDistinctRoomTypes();


//    This method retrieves a list of available rooms of a specific type that are not already
//    booked for the given date range.

    @Query("SELECT r FROM Room r WHERE r.roomType = :roomType AND r.isBooked = false " +
            "AND r.id NOT IN (SELECT b.room.id FROM Booking b WHERE b.checkInDate < :checkOutDate AND b.checkOutDate > :checkInDate)")
    List<Room> findAvailableRoomsByDatesAndType(@Param("checkInDate") LocalDate checkInDate,
                                                @Param("checkOutDate") LocalDate checkOutDate,
                                                @Param("roomType") String roomType);

    Optional<Room> findById(Long id);

    Room findByRoomType(String roomType);

}
