package com.saraWoldegiorgis.AbyssiniaHotelBookingApp;

import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.models.Room;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.repositories.RoomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.rowset.serial.SerialBlob;
import java.math.BigDecimal;
import java.sql.Blob;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class RoomRepositoryTests {

    @Autowired
    private RoomRepository roomRepository;

    @Test
    public void testFindRoomById() {
        Room room = roomRepository.findById(1L).orElse(null);
        assertThat(room).isNotNull();
        assertThat(room.getId()).isEqualTo(1L);
    }

    @Test
    public void testFindAllRooms(){
        List<Room> rooms = roomRepository.findAll();
        assertThat(rooms.size()).isGreaterThan(0);
    }

    @Test
    public void testFindRoomByType() {

        Room room = roomRepository.findByRoomType("Single");

        assertThat(room).isNotNull();
        assertThat(room.getRoomType()).isEqualTo("Single");
    }
    @Test
    public void testAddNewRoom() throws Exception {
        // A sample byte array to represent the photo
        byte[] photoBytes = "sample photo".getBytes();
        Blob photoBlob = new SerialBlob(photoBytes);

        // When: Build the Room object with the Blob
        Room room = Room.builder()
                .roomType("Presidential")
                .roomPrice(BigDecimal.valueOf(150))
                .photo(photoBlob)  // Set the photo Blob
                .build();

        roomRepository.save(room);

        // Then: Verify that the room was saved correctly
        assertThat(room.getId()).isGreaterThan(0);
    }

}
