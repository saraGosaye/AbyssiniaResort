package com.saraWoldegiorgis.AbyssiniaHotelBookingApp.services.Impl;


import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.models.Role;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.models.User;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.repositories.RoleRepository;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.repositories.UserRepository;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.services.IRoleService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService implements IRoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public RoleService(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Role saveRole(Role role) {
        role.setRoleName(role.getRoleName().trim()); //Trim the name before saving
        return roleRepository.save(role);
    }

    @Override
    @Transactional
    public Role findRoleByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName.trim());
    }

    @Override
    public Role getRoleById(long id) {
        Optional<Role> optionalRole = roleRepository.findById(id);

        if (optionalRole.isPresent()) {
            System.out.println("The role name is " + optionalRole.get().getRoleName());
            return optionalRole.get();
        }

        // Handle the case where the role is not found
        throw new EntityNotFoundException("Role with ID " + id + " not found");
    }


    @Override
    public Optional<Role> findRoleById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    @Transactional
    public void updateRole(Role role) {
        roleRepository.save(role);
    }


    @Override
    public List<Role> getAllRoles() {
        return (List<Role>) roleRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteRoleById(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role not found"));

        // Remove role from all users
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getRoles().contains(role)) {
                user.removeRole(role);
            }
        }
        userRepository.saveAll(users); // Save updated users

        roleRepository.deleteById(id); // Now delete the role
}

    public List<Role> getRolesByUser(Long id) {
        return roleRepository.findRoleByUser(id);
    }
}
