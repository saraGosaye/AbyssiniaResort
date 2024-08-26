package com.saraWoldegiorgis.AbyssiniaHotelBookingApp.controllers;

import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.models.Booking;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.services.IBookingService;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.services.IRoomService;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.services.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class BookingController {

    private final IBookingService bookingService;
    private final IRoomService roomService;
    private final IUserService userService;

    // Display the booking form
    @GetMapping("/new")
    public String showBookingForm(Model model) {
        model.addAttribute("booking", new Booking());
        model.addAttribute("rooms", roomService.findAllRooms());
        return "booking_form";
    }

    // Process the booking form submission
    @PostMapping("/new")
    public String processBooking( @ModelAttribute Booking booking, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "booking_form";
        }
        try {
            booking.calculateTotalNumberOfGuest();
            bookingService.saveBooking(booking);
            model.addAttribute("message", "Booking successful! Your confirmation code is " + booking.getBookingConfirmationCode());
            return "booking_confirmation";
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred during booking. Please try again.");
            return "booking_form";
        }
    }


    // Display a list of all bookings for a user
    @GetMapping("/all")
    public String listUserBookings(@RequestParam String email, Model model) {
        model.addAttribute("bookings", bookingService.findBookingsByUserEmail(email));
        return "my_booking";
    }

    // Cancel a specific booking
    @GetMapping("/cancel/{id}")
    public String cancelBooking(@PathVariable Long id, Model model) {
        bookingService.deleteBookingById(id);
        model.addAttribute("message", "Booking canceled successfully!");
        return "redirect:/bookings?email=" + model.getAttribute("email");
    }

//    @PostMapping("/new")
//    public String submitBooking(
//            @RequestParam("checkInDate") LocalDate checkInDate,
//            @RequestParam("checkOutDate") LocalDate checkOutDate,
//            @RequestParam("roomType") String roomType,
//            @RequestParam("numOfAdults") int numOfAdults,
//            @RequestParam("numOfChildren") int numOfChildren,
//            Model model) {
//
//        // Save the booking using the bookingService layer
////        Booking savedBooking= bookingService.addNewBooking(checkInDate,
////                checkOutDate, roomType, numOfAdults, numOfChildren);
//
//        // Add booked room to the model
////        model.addAttribute("booking", savedBooking);

          // If successful, add a success message
//          System.out.println("Booking added successfully!");
//          model.addAttribute("successMessage", "Booking added successfully!");
//          return "redirect:/bookings";
//
//    }
}
