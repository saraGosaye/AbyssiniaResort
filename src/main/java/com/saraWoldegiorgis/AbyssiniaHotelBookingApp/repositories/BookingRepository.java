package com.saraWoldegiorgis.AbyssiniaHotelBookingApp.repositories;

import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

//    Retrieves a list of BookedRoom entities based on the roomID

    List<Booking> findByRoomId(Long roomId);

//   Retrieves a Booking entity based on the booking confirmation code
//   optional is used to avoid "NullPointerException"

    Optional<Booking> findByBookingConfirmationCode(String bookingConfirmationCode);

    List<Booking> findByGuestEmail(String email);
}
