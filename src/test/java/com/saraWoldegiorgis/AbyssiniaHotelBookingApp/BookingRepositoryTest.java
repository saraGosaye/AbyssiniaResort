package com.saraWoldegiorgis.AbyssiniaHotelBookingApp;

import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.models.Booking;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.repositories.BookingRepository;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.repositories.RoomRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class BookingRepositoryTest {

    @Autowired
    private BookingRepository bookingRepository;

    @Test
    public void testFindByBookingId() {
        Booking bookings = bookingRepository.findById(1L).orElse(null);
        assertThat(bookings).isNotNull();
        assertThat(bookings.getBookingId()).isEqualTo(1L);
    }

    @Test
    public void testFindByGuestEmail() {
        List<Booking> bookings = bookingRepository.findByGuestEmail("sara@gmail.com");
        assertThat(bookings).hasSize(1);
        assertThat(bookings.get(0).getGuestEmail()).isEqualTo("sara@gmail.com");
    }

    @Test
    public void testFindByGuestEmailNotFound() {
        List<Booking> bookings = bookingRepository.findByGuestEmail("xyz@gmail.com");
        assertThat(bookings).isEmpty();
    }

    @Test
    public void deleteBookingTest() {
        // Ensure a booking with this ID exists
        Booking booking = bookingRepository.findById(1L).orElse(null);
        if (booking != null) {
            bookingRepository.delete(booking);
        }

        // Retrieve bookings by guest email
        List<Booking> bookings = bookingRepository.findByGuestEmail("sara@gmail.com");

        // Assert that the list is empty
        assertThat(bookings).isEmpty();
    }
}
