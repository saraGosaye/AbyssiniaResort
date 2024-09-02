package com.saraWoldegiorgis.AbyssiniaHotelBookingApp;

import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.models.Booking;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.models.Room;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.repositories.BookingRepository;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.repositories.RoomRepository;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.repositories.UserRepository;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.services.Impl.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BookingServiceTest {

    @Autowired
    private BookingService bookingService;

    @MockBean
    private BookingRepository bookingRepository;

    private Booking booking;
    private Room room;

    @BeforeEach
    public void setUp() {
        room = new Room();
        room.setId(1L);
        room.setRoomType("Single");
        room.setRoomPrice(new BigDecimal("100.00"));

        booking = new Booking();
        booking.setBookingId(1L);
        booking.setGuestFullName("John Doe");
        booking.setGuestEmail("john.doe@example.com");
        booking.setCheckInDate(LocalDate.now());
        booking.setCheckOutDate(LocalDate.now().plusDays(2));
        booking.setRoom(room);
        booking.setNumOfAdults(2);
        booking.setNumOfChildren(0);
    }

    @Test
    public void testFindBookingsByUserEmail() {
        String guestEmail = "john.doe@example.com";
        when(bookingRepository.findByGuestEmail(guestEmail)).thenReturn(Collections.singletonList(booking));

        List<Booking> bookings = bookingService.findBookingsByUserEmail(guestEmail);

        assertThat(bookings).isNotNull();
        assertThat(bookings.size()).isEqualTo(1);
        assertThat(bookings.get(0).getGuestEmail()).isEqualTo(guestEmail);

        verify(bookingRepository, times(1)).findByGuestEmail(guestEmail);
    }

    @Test
    public void testDeleteBookingById() {
        Long bookingId = 1L;
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

        bookingService.deleteBookingById(bookingId);

        verify(bookingRepository, times(1)).deleteById(bookingId);
    }

    @Test
    public void testAddNewBooking() {
        String guestFullName = "John Doe";
        String guestEmail = "john.doe@example.com";
        LocalDate checkInDate = LocalDate.now();
        LocalDate checkOutDate = LocalDate.now().plusDays(2);
        int numOfAdults = 2;
        int numOfChildren = 0;

        Booking newBooking = new Booking();
        newBooking.setGuestFullName(guestFullName);
        newBooking.setGuestEmail(guestEmail);
        newBooking.setCheckInDate(checkInDate);
        newBooking.setCheckOutDate(checkOutDate);
        newBooking.setRoom(room);
        newBooking.setNumOfAdults(numOfAdults);
        newBooking.setNumOfChildren(numOfChildren);

        when(bookingRepository.save(any(Booking.class))).thenReturn(newBooking);

        Booking addedBooking = bookingService.addNewBooking(guestFullName, guestEmail, checkInDate, checkOutDate, room, numOfAdults, numOfChildren);

        assertThat(addedBooking).isNotNull();
        assertThat(addedBooking.getGuestFullName()).isEqualTo(guestFullName);
        assertThat(addedBooking.getGuestEmail()).isEqualTo(guestEmail);

        verify(bookingRepository, times(1)).save(any(Booking.class));
    }
}
