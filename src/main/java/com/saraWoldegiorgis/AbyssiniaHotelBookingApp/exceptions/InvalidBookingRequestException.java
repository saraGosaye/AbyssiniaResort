package com.saraWoldegiorgis.AbyssiniaHotelBookingApp.exceptions;


public class InvalidBookingRequestException extends RuntimeException {
    public InvalidBookingRequestException(String message) {
        super(message);
    }
}
