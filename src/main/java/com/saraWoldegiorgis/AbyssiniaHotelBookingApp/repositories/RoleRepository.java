package com.saraWoldegiorgis.AbyssiniaHotelBookingApp.repositories;

import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleName(String roleName);

    boolean existsByRoleName(String role);
}
