package com.saraWoldegiorgis.AbyssiniaHotelBookingApp.services;

import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.models.Role;

import java.util.List;

public interface IRoleService {

    void saveRole(Role role);
    Role findRoleByRoleName(String roleName);
    List<Role> getRolesByUser(long id);
    List<Role> findAllRoles();
    void deleteRoleById(Long id);


//    Role findRoleByName(String roleName);
//    List<Role> getAllRoles();


}
