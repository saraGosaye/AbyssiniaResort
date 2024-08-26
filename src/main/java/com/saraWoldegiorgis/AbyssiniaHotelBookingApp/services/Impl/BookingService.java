package com.saraWoldegiorgis.AbyssiniaHotelBookingApp.services.Impl;

import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.exceptions.ResourceNotFoundException;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.models.Booking;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.repositories.BookingRepository;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.services.IBookingService;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.services.IRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService implements IBookingService {
    private final BookingRepository bookingRepository;
    private final IRoomService roomService;

    @Override
    public List<Booking> findAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public List<Booking> findBookingsByUserEmail(String guestEmail) {
        return bookingRepository.findByGuestEmail(guestEmail);
    }

    @Override
    public void deleteBookingById(Long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public List<Booking> findAllBookingsByRoomId(Long roomId) {
        return bookingRepository.findByRoomId(roomId);
    }


    @Override
    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

//    @Override
//    public Booking addNewBooking(LocalDate checkInDate, LocalDate checkOutDate, String roomType, int numOfAdults, int numOfChildren) {
//        Booking booking= new Booking();
//        booking.setCheckInDate(checkInDate);
//        booking.setCheckOutDate(checkOutDate);
//        booking.setRoomType(roomType);
//        booking.setNumOfAdults(numOfAdults);
//        booking.setNumOfChildren(numOfChildren);
//
//        return bookingRepository.save(booking);
//    }

    @Override
    public Optional<Booking> findBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    @Override
    public Booking findByBookingConfirmationCode(String bookingConfirmationCode) {
        return bookingRepository.findByBookingConfirmationCode(bookingConfirmationCode)
                .orElseThrow(() -> new ResourceNotFoundException("No booking found with booking " +
                        "code :"+bookingConfirmationCode));

    }
}
