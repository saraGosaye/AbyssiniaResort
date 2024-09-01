package com.saraWoldegiorgis.AbyssiniaHotelBookingApp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotEmpty;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rooms")

// Maps to the "rooms" table and includes a one-to-many relationship with bookings.
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotEmpty(message = "Room Type cannot be empty")
    @Column(name = "room_type", nullable = false, unique = true)
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

    @JsonIgnore
    @OneToMany(mappedBy="room", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval =
            true)
    private List<Booking> bookings;

}
