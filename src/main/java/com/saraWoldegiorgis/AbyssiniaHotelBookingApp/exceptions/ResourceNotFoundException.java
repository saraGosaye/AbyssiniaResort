package com.saraWoldegiorgis.AbyssiniaHotelBookingApp.exceptions;


public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
