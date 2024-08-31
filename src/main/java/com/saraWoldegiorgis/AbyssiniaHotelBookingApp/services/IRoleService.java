package com.saraWoldegiorgis.AbyssiniaHotelBookingApp.services;

import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.models.Role;

import java.util.List;
import java.util.Optional;

public interface IRoleService {

//    void saveRole(Role role);
    Role findRoleByRoleName(String roleName);
    Role getRoleById(long id);
    List<Role> getAllRoles();
    void deleteRoleById(Long id);
    Optional<Role> findRoleById(Long id);

    void updateRole(Role role);
    Role saveRole(Role role);

//    Role findRoleByName(String roleName);
}
