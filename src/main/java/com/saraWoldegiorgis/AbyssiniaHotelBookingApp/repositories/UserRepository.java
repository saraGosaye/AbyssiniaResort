package com.saraWoldegiorgis.AbyssiniaHotelBookingApp.repositories;

import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findUserById(Long id);

    User findUserByUserName(String name);

    boolean existsByEmail(String email);
    void deleteByEmail(String email);
}
