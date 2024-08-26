package com.saraWoldegiorgis.AbyssiniaHotelBookingApp.repositories;

import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

//    We might have different types of rooms for a typical room like 3 different single rooms with
//    different styles & prices. But here we just need unique room types available in the hotel

    @Query("SELECT DISTINCT r.roomType FROM Room r")
    List<String> findDistinctRoomTypes();


//    This method retrieves a list of available rooms of a specific type that are not already
//    booked for the given date range.

    @Query(" SELECT r FROM Room r " +
            " WHERE r.roomType LIKE %:roomType% " +
            " AND r.id NOT IN (" +
            "  SELECT br.room.id FROM Booking br " + //identifies rooms that are already booked during the specified date
            "  WHERE ((br.checkInDate <= :checkOutDate) AND (br.checkOutDate >= :checkInDate))" +
            ")") // selects rooms that are not part of the booked rooms, effectively filtering out unavailable rooms.


    List<Room> findAvailableRoomsByDatesAndType(LocalDate checkInDate, LocalDate checkOutDate,
                                                String roomType);

}
