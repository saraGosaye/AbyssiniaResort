package com.saraWoldegiorgis.AbyssiniaHotelBookingApp.controllers;

import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.dto.BookingForm;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.models.Booking;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.models.Room;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.services.IBookingService;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.services.IRoomService;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.services.IUserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/bookings")
public class BookingController {
//    Handles HTTP requests related to bookings. Manages booking creation, cancellation, and
//    lookup by guest email, and provides views for booking forms and booking details.

    private final IBookingService bookingService;
    private final IRoomService roomService;

    public BookingController(IBookingService bookingService, IRoomService roomService) {
        this.bookingService = bookingService;
        this.roomService = roomService;
    }

    @ModelAttribute("bookingForm")
    public BookingForm bookingForm() {
        System.out.println("IN  BookingController->BookingForm()");
        return new BookingForm();
    }

    @GetMapping("/new")
    public String showBookingForm(Model model) {

        // Fetch the list of rooms from the service
        List<Room> rooms = roomService.findAllRooms();

        // Add the list of rooms to the model
        model.addAttribute("rooms", rooms);

        // Add an empty BookingForm to the model
        model.addAttribute("bookingForm", new BookingForm());
        System.out.println("===========>IN displayBookingForm ");
        return "booking_form";
    }

    @PostMapping("/new")
    public String addNewBooking(@Valid @ModelAttribute("bookingForm") BookingForm bookingForm, BindingResult result, Model
    model) {
     System.out.println("===========>IN addingNewBooking() ");
        if (result.hasErrors()) {
            // Return to the form with validation errors
            System.out.println("Has Errors: " + result.hasErrors());
            return "booking_form";
        }

        if (!bookingForm.isValidDateRange()) {
            model.addAttribute("errorMessage", "Check-out date must be after check-in date.");
            return "booking_form";
        }

        try {
            Room room = roomService.findRoomByType(bookingForm.getRoomType());

            if (room == null) {
                throw new IllegalArgumentException("Room type not found.");
            }
            Booking booking = bookingService.addNewBooking(
                    bookingForm.getGuestFullName(),
                    bookingForm.getGuestEmail(),
                    bookingForm.getCheckInDate(),
                    bookingForm.getCheckOutDate(),
                    room,
                    bookingForm.getNumOfAdults(),
                    bookingForm.getNumOfChildren()
            );
            model.addAttribute("booking", booking);
            return "booking_details";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "booking_form";
        }
    }

    @GetMapping("/cancel/{id}")
    public String cancelBooking(@PathVariable Long id, Model model) {
        bookingService.deleteBookingById(id);
        model.addAttribute("successMessage", "Booking has been successfully canceled.");
        return "redirect:/home";
    }

    // Display the form to find bookings by email
    @GetMapping("/my_booking")
    public String showFindMyBookingForm(Model model) {
        model.addAttribute("email", new String()); // To bind the form data
        return "my_booking";
    }

    // Process the form submission to find bookings by email
    @PostMapping("/my_booking")
    public String findBookingByEmail(@RequestParam("guestEmail") String guestEmail, Model model) {

        List<Booking> bookingList = bookingService.findBookingsByUserEmail(guestEmail);

        // Add the booking details to the model
        if (bookingList.isEmpty()) {
            model.addAttribute("message", "No bookings found for this email.");
        } else {
            model.addAttribute("booking", bookingList);
        }
        return "booking_details";
    }

}
