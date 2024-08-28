package com.saraWoldegiorgis.AbyssiniaHotelBookingApp.services;

import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.models.Room;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IRoomService {
    Room addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice) throws IOException, SQLException;

    Room saveRoom(Room room);

    List<String> findAllRoomTypes();

    List<Room> findAllRooms();

    Optional<Room> findRoomById(Long roomId);

    void deleteRoomById(Long roomId);

    Room updateRoom(Long roomId, String roomType, BigDecimal roomPrice, byte[] photoBytes);

    List<Room> findAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate, String roomType);

    Blob getImageBlobById(Long id);
}


