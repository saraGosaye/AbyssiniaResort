package com.saraWoldegiorgis.AbyssiniaHotelBookingApp.services;

import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.dto.UserDTO;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IUserService extends UserDetailsService {

    UserDetails loadUserByUsername(String userName);
    User findUserByEmail(String email);
    User findUserById(Long id);
    void creat(UserDTO userDTO);

//    List<User> findAllUsers();
//    void deleteUserByEmail(String email);
//    void addRoleToUser(String email, String roleName);
}
