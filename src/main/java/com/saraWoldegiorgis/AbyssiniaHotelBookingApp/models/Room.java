package com.saraWoldegiorgis.AbyssiniaHotelBookingApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import jakarta.validation.constraints.NotEmpty;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotEmpty(message = "Room Type cannot be empty")
    @Column(name = "room_type", nullable = false)
    private String roomType;

    @NotNull(message = "Room Price cannot be null")
    @Column(name = "room_price", nullable = false)
    private BigDecimal roomPrice;

    @Column(name = "is_Booked", nullable = false)
    private boolean isBooked = false;

    @Lob
    @NotNull(message = "Please upload a photo.")
    @Column(name = "Photo", nullable = false)
    private Blob photo;

    @OneToMany(mappedBy="room", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings;

    public void addBooking(Booking booking){
        if (bookings == null){
            bookings = new ArrayList<>();
        }
        bookings.add(booking);
        booking.setRoom(this);
        isBooked = true;
        String bookingCode = RandomStringUtils.randomNumeric(10);
        booking.setBookingConfirmationCode(bookingCode);
    }

}
