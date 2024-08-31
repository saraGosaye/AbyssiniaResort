package com.saraWoldegiorgis.AbyssiniaHotelBookingApp.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingForm {

//    to collect form data from a user
    @NotBlank(message = "Guest name is required.")
    private String guestFullName;

    @NotBlank(message = "Email is required.")
    @Email(message = "Please provide a valid email address.")
    private String guestEmail;

    @NotNull(message = "Check-in date is required.")
    @Future(message = "Check-in date must be in the future.")
    private LocalDate checkInDate;

    @NotNull(message = "Check-out date is required.")
    @Future(message = "Check-out date must be in the future.")
    private LocalDate checkOutDate;

    @NotBlank(message = "Room type is required.")
    private String roomType;

    @Min(value = 1, message = "At least one adult is required.")
    private int numOfAdults;

    @Min(value = 0, message = "Number of children cannot be negative.")
    private int numOfChildren;

    // Additional validation to ensure check-out is after check-in
    public boolean isValidDateRange() {
        return checkOutDate != null && checkInDate != null && checkOutDate.isAfter(checkInDate);
    }

}
