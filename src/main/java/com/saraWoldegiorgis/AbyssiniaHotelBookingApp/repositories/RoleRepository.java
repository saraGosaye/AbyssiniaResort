package com.saraWoldegiorgis.AbyssiniaHotelBookingApp.repositories;

import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleName(String roleName);

    @Query(value = "select * from role where role.id= (select role_id from users_roles where user_id = :id)", nativeQuery = true)
    List<Role> findRoleByUser(@Param("id") long id);

    void deleteRoleById(Long id);
}
