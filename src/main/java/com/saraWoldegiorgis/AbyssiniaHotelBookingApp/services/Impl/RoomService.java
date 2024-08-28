package com.saraWoldegiorgis.AbyssiniaHotelBookingApp.services.Impl;


import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.models.Room;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.repositories.RoomRepository;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.services.IRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService implements IRoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public Room addNewRoom(MultipartFile file, String roomType, BigDecimal roomPrice) throws IOException, SQLException {

        Room room = new Room();
        room.setRoomType(roomType);
        room.setRoomPrice(roomPrice);

        if (!file.isEmpty()){
            byte[] photoBytes = file.getBytes();
            Blob photoBlob = new SerialBlob(photoBytes);
            room.setPhoto(photoBlob);
        }

        return roomRepository.save(room);
    }

    @Override
    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public Optional<Room> findRoomById(Long id) {
        return Optional.of(roomRepository.findById(id).get());
    }

    @Override
    public List<Room> findAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public List<String> findAllRoomTypes() {
        return roomRepository.findDistinctRoomTypes();
    }

    public void deleteRoomById(Long id) {
        try {
            Optional<Room> theRoom = roomRepository.findById(id);
            if (theRoom.isPresent()) {
                roomRepository.deleteById(id);
                System.out.println("Room with ID " + id + " deleted successfully.");
            } else {
                System.out.println("Room with ID " + id + " not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Room updateRoom(Long roomId, String roomType, BigDecimal roomPrice, byte[] photoBytes) {
        Room room = roomRepository.findById(roomId).orElse(null);
        if (room == null) {
            // Handle case where room is not found
            throw new IllegalArgumentException("Room not found with ID: " + roomId);
        }
        if (roomType != null) room.setRoomType(roomType);
        if (roomPrice != null) room.setRoomPrice(roomPrice);
        if (photoBytes != null && photoBytes.length > 0) {
            try {
                room.setPhoto(new SerialBlob(photoBytes));
            } catch (SQLException ex) {
                throw new RuntimeException("Failed to update room photo", ex);
            }
        }
        return roomRepository.save(room);
    }

    @Override
    public List<Room> findAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate,
                                      String roomType) {
        return roomRepository.findAvailableRoomsByDatesAndType(checkInDate, checkOutDate, roomType);
    }

    @Override
    public Blob getImageBlobById(Long id) {
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (optionalRoom.isPresent()) {
            Room room = optionalRoom.get();
            return room.getPhoto(); // `getPhoto()` will return a Blob
        } else {
            throw new ResourceNotFoundException("Room not found with id " + id);
        }
    }
    public static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }

}
