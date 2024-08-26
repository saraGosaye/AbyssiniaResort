package com.saraWoldegiorgis.AbyssiniaHotelBookingApp.services;


import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.models.Booking;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IBookingService {
    Booking saveBooking(Booking booking);

//    Booking addNewBooking(LocalDate checkInDate, LocalDate checkOutDate, String roomType, int numOfAdults, int numOfChildren);

    Optional<Booking> findBookingById(Long bookingId);

    List<Booking> findAllBookings();

    void deleteBookingById(Long bookingId);

    Booking findByBookingConfirmationCode(String bookingConfirmationCode);

    List<Booking> findAllBookingsByRoomId(Long roomId);

    List<Booking> findBookingsByUserEmail(String guestEmail);
}
