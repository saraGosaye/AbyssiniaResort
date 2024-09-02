package com.saraWoldegiorgis.AbyssiniaHotelBookingApp;

import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.models.Room;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.repositories.RoomRepository;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.services.Impl.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RoomServiceTest {

    @Autowired
    private RoomService roomService;

    @MockBean
    private RoomRepository roomRepository;

    private Room room;

    @BeforeEach
    public void setUp() {
        room = new Room();
        room.setId(1L);
        room.setRoomType("Single");
        room.setRoomPrice(new BigDecimal("100.00"));
    }

    @Test
    public void testAddNewRoom() throws Exception {
        // Mocking a MultipartFile
        MultipartFile file = mock(MultipartFile.class);
        byte[] photoBytes = "photo".getBytes();

        // Simulate the behavior of the file
        when(file.getBytes()).thenReturn(photoBytes);
        when(file.isEmpty()).thenReturn(false);

        // Mocking the repository save method
        when(roomRepository.save(any(Room.class))).thenAnswer(invocation -> {
            Room savedRoom = invocation.getArgument(0);
            savedRoom.setId(1L); // Simulate the database generating an ID
            return savedRoom;
        });

        // Call the service method
        Room savedRoom = roomService.addNewRoom(file, "Single", new BigDecimal("100.00"));

        // Assert that the room was saved with the correct data
        assertThat(savedRoom.getRoomType()).isEqualTo("Single");
        assertThat(savedRoom.getRoomPrice()).isEqualTo(new BigDecimal("100.00"));

        // Assert that the photo field is not null and contains the correct blob
        assertThat(savedRoom.getPhoto()).isNotNull();
        assertThat(savedRoom.getPhoto()).isEqualTo(new SerialBlob(photoBytes));

        // Verify that the repository's save method was called exactly once
        verify(roomRepository, times(1)).save(any(Room.class));
    }

    @Test
    public void testFindRoomById() {
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));

        Optional<Room> foundRoom = roomService.findRoomById(1L);

        assertThat(foundRoom.isPresent()).isTrue();
        assertThat(foundRoom.get().getId()).isEqualTo(1L);

        verify(roomRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindAllRooms() {
        Room room2 = new Room();
        room2.setId(2L);
        room2.setRoomType("Double");
        room2.setRoomPrice(new BigDecimal("150.00"));

        when(roomRepository.findAll()).thenReturn(Arrays.asList(room, room2));

        List<Room> rooms = roomService.findAllRooms();

        assertThat(rooms).hasSize(2);
        assertThat(rooms.get(0).getRoomType()).isEqualTo("Single");
        assertThat(rooms.get(1).getRoomType()).isEqualTo("Double");

        verify(roomRepository, times(1)).findAll();
    }

}
