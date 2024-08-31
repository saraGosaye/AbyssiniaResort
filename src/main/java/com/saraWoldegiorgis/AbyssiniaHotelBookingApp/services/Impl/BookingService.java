package com.saraWoldegiorgis.AbyssiniaHotelBookingApp.services.Impl;

import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.models.Booking;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.models.Room;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.models.User;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.repositories.BookingRepository;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.repositories.RoomRepository;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.repositories.UserRepository;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.services.IBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService implements IBookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    @Override
    public List<Booking> findAllBookings() {
        return (List<Booking>) bookingRepository.findAll();
    }

    @Override
    public List<Booking> findBookingsByUserEmail(String guestEmail) {
        return bookingRepository.findByGuestEmail(guestEmail);
    }

    @Override
    public void deleteBookingById(Long bookingId) {
        bookingRepository.deleteById(bookingId);
    }

    @Override
    public List<Booking> findAllBookingsByRoomId(Long roomId) {
        return bookingRepository.findByRoomId(roomId);
    }

    @Override
    public Optional<Booking> findBookingById(Long bookingId) {
        return Optional.of(bookingRepository.findById(bookingId).get());
    }

    @Override
    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public Booking addNewBooking(String guestFullName, String guestEmail, LocalDate checkInDate, LocalDate checkOutDate, Room room, int numOfAdults, int numOfChildren) {

        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null.");
        }

        // Create a new Booking object
        Booking newBooking = new Booking();
        newBooking.setGuestFullName(guestFullName);
        newBooking.setGuestEmail(guestEmail);
        newBooking.setCheckInDate(checkInDate);
        newBooking.setCheckOutDate(checkOutDate);
        newBooking.setRoom(room);
        newBooking.setNumOfAdults(numOfAdults);
        newBooking.setNumOfChildren(numOfChildren);

        // Save the new booking to the database
        bookingRepository.save(newBooking);

        // Return the newly created booking
        return newBooking;
    }

}
