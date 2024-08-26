package com.saraWoldegiorgis.AbyssiniaHotelBookingApp.services;

import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.dto.UserRegistrationDto;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface IUserService extends UserDetailsService {

    User save(UserRegistrationDto registration);
    User findUserByEmail(String email);
    User findUserByName(String name);
    void creat(UserDTO userDTO);

    List<User> findAllUsers();
    void deleteUserByEmail(String email);
    void addRoleToUser(String email, String roleName);
}
