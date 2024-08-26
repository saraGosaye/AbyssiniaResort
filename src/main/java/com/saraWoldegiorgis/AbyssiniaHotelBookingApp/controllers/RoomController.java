package com.saraWoldegiorgis.AbyssiniaHotelBookingApp.controllers;

import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.models.Room;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.services.IRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class RoomController {

    private final IRoomService roomService;

    // Display the home page
    @RequestMapping("/")
    public String home() {
        System.out.println("===========>IN displayHomePage() ");
        return "home_page";
}

    // Display the form to add a new room
    @GetMapping("/add_room")
    public String showAddRoomForm(Model model) {
        model.addAttribute("room", new Room());
        System.out.println("===========>IN displayAddRoomForm ");
        return "room_add";
    }

    // Handle the submission of the form to add a new room
    @PostMapping("/add_room")
    public String addNewRoom(
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("roomType") String roomType,
            @RequestParam("roomPrice") BigDecimal roomPrice,
            Model model) {

        try {
            // Save the room using the roomService layer
            Room savedRoom = roomService.addNewRoom(photo, roomType, roomPrice);

            // Add the saved room to the model to display its details
            model.addAttribute("room", savedRoom);

            // If successful, add a success message
            System.out.println("Room added successfully!");
            model.addAttribute("successMessage", "Room added successfully!");
            return "redirect:/rooms";

        } catch (IOException | SQLException e) {
            // If an error occurs, add a failure message
            System.out.println("Failed to add Room!");
            model.addAttribute("failureMessage", "Failed to add new room. Please try again.");
            return "room_add";
        }
    }
    // List all rooms
    @GetMapping("/rooms")
    public String findAllRooms(Model model) {
        List<Room> rooms = roomService.findAllRooms();
        model.addAttribute("rooms", rooms);
        return "rooms";
    }

    // Show room details
    @GetMapping("/room/{id}")
    public String findRoomById(@PathVariable Long id, Model model) {
        Optional<Room> room = roomService.findRoomById(id);
        if (room.isPresent()) {
            model.addAttribute("room", room);
            return "room_details";
        } else {
            return "redirect:/rooms";
        }
    }
    // Show form to update a room
    @GetMapping("/edit/{id}")
    public String showUpdateRoomForm(@PathVariable Long id, Model model) {
        Optional<Room> room = roomService.findRoomById(id);
        if (room.isPresent()) {
            model.addAttribute("room", room.get());
            return "room_edit";
        } else {
            return "redirect:/rooms";
        }
    }

    // Handle the submission of the form to update a room
    @PostMapping("/edit/{id}")
    public String updateRoom(@PathVariable Long id,
                             @RequestParam("roomType") String roomType,
                             @RequestParam("roomPrice") BigDecimal roomPrice,
                             @RequestParam(value = "photo", required = false) MultipartFile photo,
                             Model model) {
        try {
            byte[] photoBytes = photo != null ? photo.getBytes() : null;
            roomService.updateRoom(id, roomType, roomPrice, photoBytes);
            return "redirect:/rooms";
        } catch (IOException e) {
            model.addAttribute("error", "Failed to update room");
            return "room_edit";
        }
    }
    // Delete a room
    @GetMapping("/delete/{id}")
    public String deleteRoomById(@PathVariable Long id, Model model) {
        roomService.deleteRoomById(id);
        model.addAttribute("message", "room canceled successfully!");
        return "redirect:/rooms";
    }
    // Find available rooms by check-in and check-out dates and room type
    @GetMapping("/available")
    public String findAvailableRooms(@RequestParam("checkInDate") String checkInDateStr,
                                     @RequestParam("checkOutDate") String checkOutDateStr,
                                     @RequestParam("roomType") String roomType,
                                     Model model) {
        LocalDate checkInDate = LocalDate.parse(checkInDateStr);
        LocalDate checkOutDate = LocalDate.parse(checkOutDateStr);
        List<Room> availableRooms = roomService.findAvailableRooms(checkInDate, checkOutDate, roomType);
        model.addAttribute("availableRooms", availableRooms);
        return "available_rooms";
    }
}
