package com.saraWoldegiorgis.AbyssiniaHotelBookingApp.services;

import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.models.Role;

import java.util.List;
import java.util.Optional;

public interface IRoleService {

    void saveRole(Role role);
    Role findRoleByRoleName(String roleName);
    List<Role> getRolesByUser(long id);
    List<Role> getAllRoles();
    void deleteRoleById(Long id);
    Optional<Role> findRoleById(Long id);


//    Role findRoleByName(String roleName);
}
