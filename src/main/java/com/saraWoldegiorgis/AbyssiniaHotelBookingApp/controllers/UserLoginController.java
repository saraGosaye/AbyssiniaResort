package com.saraWoldegiorgis.AbyssiniaHotelBookingApp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserLoginController {
    @GetMapping("/")
    public String root() {
        System.out.println("IN root()  redirecting to customers");
        return "redirect:/customers";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }
}



//private final IUserService userService;
//// Show a form to create a new user
//@GetMapping("/new")
//public String showUserForm(Model model) {
//    model.addAttribute("user", new User());
//    return "user_registration_form";
//}
//
//// Save a new user
//@PostMapping("/save")
//public String saveUser(@ModelAttribute("user") User user) {
//    userService.saveUser(user);
//    return "redirect:/users/all";
//}

//// Show all users
//@GetMapping("/all")
//public String listUsers(Model model) {
//    List<User> users = userService.findAllUsers();
//    model.addAttribute("users", users);
//    return "user_list";
//}
//// Show user details by email
//@GetMapping("/find/{email}")
//public String findUserByEmail(@PathVariable String email, Model model) {
//    Optional<User> user = userService.findUserByEmail(email);
//    model.addAttribute("user", user);
//    return "user_details";
//}
//
//// Show a form to add a role to a user
//@GetMapping("/addRole")
//public String showAddRoleForm(Model model) {
//    model.addAttribute("emails", userService.findAllUsers().stream().map(User::getEmail).toList());
//    model.addAttribute("roles", List.of("ROLE_USER", "ROLE_ADMIN")); // or fetch from the database
//    return "role_add_form";
//}
//
//// Add a role to a user
//@PostMapping("/addRole")
//public String addRoleToUser(@RequestParam String email, @RequestParam String roleName) {
//    userService.addRoleToUser(email, roleName);
//    return "redirect:/users/all";
//}
//// Delete a user by email
//@PostMapping("/delete")
//public String deleteUser(@RequestParam String email) {
//    userService.deleteUserByEmail(email);
//    return "redirect:/users/all";
//}