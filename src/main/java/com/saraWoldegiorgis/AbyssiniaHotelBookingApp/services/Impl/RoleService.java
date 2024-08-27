package com.saraWoldegiorgis.AbyssiniaHotelBookingApp.services.Impl;


import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.models.Role;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.repositories.RoleRepository;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.services.IRoleService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService implements IRoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void saveRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    @Transactional
    public Role findRoleByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    @Override
    public List<Role> getAllRoles() {
        return (List<Role>) roleRepository.findAll();
    }

    @Override
    public List<Role> getRolesByUser(long id) {
        return roleRepository.findRoleByUser(id);
    }

    @Override
    public Optional<Role> findRoleById(Long id) {
        return roleRepository.findById(id);
    }


    @Override
    public void deleteRoleById(Long id) {
        roleRepository.deleteRoleById(id);
    }

}
