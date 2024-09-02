package com.saraWoldegiorgis.AbyssiniaHotelBookingApp.services;


import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.dto.BookingForm;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.models.Booking;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.models.Room;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IBookingService {

    List<Booking> findBookingsByUserEmail(String guestEmail);
    void deleteBookingById(Long bookingId);


    Booking saveBooking(Booking booking);
    Optional<Booking> findBookingById(Long id);
    List<Booking> findAllBookings();
    List<Booking> findAllBookingsByRoomId(Long roomId);
    Booking addNewBooking(String guestFullName, String guestEmail, LocalDate checkInDate, LocalDate checkOutDate, Room room, int numOfAdults, int numOfChildren);

    Booking updateBooking(Long id, String guestFullName, String guestEmail, LocalDate checkInDate, LocalDate checkOutDate, Room room, int numOfAdults, int numOfChildren);
}
