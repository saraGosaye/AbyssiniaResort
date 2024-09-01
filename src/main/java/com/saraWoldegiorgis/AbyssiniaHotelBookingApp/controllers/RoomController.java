package com.saraWoldegiorgis.AbyssiniaHotelBookingApp.controllers;

import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.models.Room;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.services.IRoomService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Controller
public class RoomController {
//    Manages HTTP requests for room-related operations. Includes functionality for adding,
//    updating, deleting, and viewing rooms, as well as handling room images.

    private final IRoomService roomService;

    public RoomController(IRoomService roomService) {
        this.roomService = roomService;
    }

    // Display the home page
    @RequestMapping("/home")
    public String home() {
        System.out.println("===========>IN displayHomePage() ");
        return "home";
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
    @GetMapping("/details/{id}")
    public String findRoomById(@PathVariable Long id, Model model) {
        Optional<Room> room = roomService.findRoomById(id);
        if (room.isPresent()) {
            model.addAttribute("room", room.get());
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

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) {
    try {
        // Retrieve the image Blob from the database using the provided ID
        Blob blob = roomService.getImageBlobById(id);

        // Check if the Blob is null
        if (blob == null) {
            // Return a 404 Not Found response if the image does not exist
            return ResponseEntity.notFound().build();
        }

        // Convert the Blob to a byte array
        byte[] imageData = blob.getBytes(1, (int) blob.length());

        // Create headers for the response
        HttpHeaders headers = new HttpHeaders();

        // Set the content type of the response to JPEG
        headers.setContentType(MediaType.IMAGE_JPEG);

        // Return the image data along with the headers and a 200 OK status
        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    } catch (SQLException e) {
        // Log the exception
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}






