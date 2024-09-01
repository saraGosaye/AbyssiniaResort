package com.saraWoldegiorgis.AbyssiniaHotelBookingApp.controllers;

import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.dto.UserDTO;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.services.Impl.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
public class UserController {
//    Handles HTTP requests related to user registration and login. Manages user sign-up and login
//    processes, including form validation and user account creation.

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    private UserService userDetailsService;

    @Autowired
    public UserController(UserService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/sign-up")
    public String signUp(Model model)
    {
        model.addAttribute("userDto", new UserDTO());
        return "sign-up";
    }

    @PostMapping("/signup-process")
    public String signupProcess(@Valid @ModelAttribute ("userDto") UserDTO userDTO,
                                BindingResult bindingResult)
    {
        System.out.println("===========>IN registerUserAccount() ");

        if(bindingResult.hasErrors())
        {
            log.warn("Registration attempt failed due to validation errors.");
            return "sign-up";
        }

        userDetailsService.creat(userDTO);
        return "redirect:/login?success";
    }

    @GetMapping("/login")
    public String getLoginPage()
    {
        log.info("Login page displayed");
        return "login";
    }

}

