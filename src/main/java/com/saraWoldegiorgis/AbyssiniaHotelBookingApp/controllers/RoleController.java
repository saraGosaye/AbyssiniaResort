package com.saraWoldegiorgis.AbyssiniaHotelBookingApp.controllers;


import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.models.Role;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.services.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class RoleController {
    private final IRoleService roleService;

    @GetMapping("/roles")
    public String listRoles(Model model) {
        List<Role> roles = roleService.findAllRoles();
        model.addAttribute("roles", roles);
        return "roles";
    }

    @GetMapping("/roles/new")
    public String showRoleForm(Model model) {
        Role role = new Role();
        model.addAttribute("role", role);
        return "role_add_form";
    }

    @PostMapping("/roles")
    public String saveRole(@ModelAttribute("role") Role role) {
        roleService.saveRole(role);
        return "redirect:/roles";
    }
    @GetMapping("/roles/edit/{id}")
    public String showEditRoleForm(@PathVariable("id") Long id, Model model) {
        Optional<Role> role = roleService.findRoleById(id);
        model.addAttribute("role", role);
        return "role_add_form";
    }

    @PostMapping("/roles/{id}")
    public String updateRole(@PathVariable("id") Long id, @ModelAttribute("role") Role role) {
        Optional<Role> existingRoleOpt = roleService.findRoleById(id);

        if (existingRoleOpt.isPresent()) {
            Role existingRole = existingRoleOpt.get();
            existingRole.setRoleName(role.getRoleName());
            roleService.saveRole(existingRole);
        } else {
            // Handle the case where the role was not found
            return "redirect:/roles?error=RoleNotFound";
        }
        return "redirect:/roles";
    }

    @GetMapping("/roles/delete/{id}")
    public String deleteRole(@PathVariable("id") Long id) {
        roleService.deleteRoleById(id);
        return "redirect:/roles";
    }
}
