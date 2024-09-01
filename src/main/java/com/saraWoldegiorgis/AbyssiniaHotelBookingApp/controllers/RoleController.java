package com.saraWoldegiorgis.AbyssiniaHotelBookingApp.controllers;


import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.models.Role;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.services.IRoleService;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.services.Impl.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class RoleController {
//    Manages HTTP requests related to roles.
//    Provides functionality to list, add, edit, and delete roles, and includes validation and error handling.

    private final IRoleService roleService;

    @Autowired
    private UserService userService;

    public RoleController(IRoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/roles")
    public String listRoles(Model model) {
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
        return "roles";
    }

    @GetMapping("/roles/new")
    public String showRoleForm(Model model) {
        Role role = new Role();
        model.addAttribute("role", role);
        return "role_add_form";
    }

    @PostMapping("/roles/new")
    public String saveRole(@ModelAttribute("role") Role role, BindingResult result, Model model) {
        Role existingRole = roleService.findRoleByRoleName(role.getRoleName());

        if (existingRole != null && existingRole.getRoleName() != null && !existingRole.getRoleName().isEmpty()) {
            result.rejectValue("roleName", null, "There is already a role registered with the same name");
        }

        if (result.hasErrors()) {
            model.addAttribute("role", role);
            return "role_add_form";
        }
        roleService.saveRole(role);
        return "redirect:/roles";
    }

    @GetMapping("/roles/edit/{id}")
    public String showEditRoleForm(@PathVariable("id") Long id, Model model) {
        Optional<Role> role = roleService.findRoleById(id);
        if(role.isPresent()){
            model.addAttribute("role", role.get());
            return "role_edit_form";
        } else{
            return "redirect:/roles";
        }

    }

    @PostMapping("/roles/edit/{id}")
    public String updateRole(@PathVariable Long id,
                             @RequestParam("roleName") String roleName,
                            Model model) {

        if (roleName == null || roleName.trim().isEmpty()) {
            model.addAttribute("error", "Role name cannot be empty");
            return "role_edit_form";
        }
        try {
            // Fetch the existing role from the database
            Role role = roleService.getRoleById(id);

            // Update the role's name with the new value from the form
            role.setRoleName(roleName);

            // Save the updated role back to the database
            roleService.updateRole(role);

            return "redirect:/roles";
        } catch (Exception e){
            model.addAttribute("error", "Failed to update role");
            return "role_edit_form";
        }
    }

    @GetMapping("/roles/delete/{id}")
    public String deleteRole(@PathVariable("id") Long id) {
        roleService.deleteRoleById(id);
        System.out.println("Id number " + id + " is deleted!");
        return "redirect:/roles";
    }

}


//    @PostMapping("/roles/new")
//    public String addRoleToUser(@RequestParam("email") String email,
//                                @RequestParam("roleName") String roleName,
//                                Model model) {
//
//        // Validate inputs
//        if (email == null || email.trim().isEmpty()) {
//            model.addAttribute("error", "Email cannot be empty");
//            return "role_add_form";
//        }
//
//        if (roleName == null || roleName.trim().isEmpty()) {
//            model.addAttribute("error", "Role name cannot be empty");
//            return "role_add_form";
//        }
//
//        try {
//            // Find the user by email
//            User user = userService.findUserByEmail(email);
//            if (user == null) {
//                model.addAttribute("error", "User not found with email: " + email);
//                return "role_add_form";
//            }
//
//            // Create a new role and assign it to the user
//            Role role = new Role();
//            role.setRoleName(roleName);
//            user.addRole(role);
//
//            // Save the role (and update the user if needed)
//            roleService.updateRole(role);
//            return "redirect:/roles";
//
//        } catch (Exception e) {
//            model.addAttribute("error", "Failed to add role to user");
//            return "role_add_form";
//        }
//    }
