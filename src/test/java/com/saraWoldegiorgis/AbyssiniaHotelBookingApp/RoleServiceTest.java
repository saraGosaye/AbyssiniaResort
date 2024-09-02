package com.saraWoldegiorgis.AbyssiniaHotelBookingApp;

import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.models.Role;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.repositories.RoleRepository;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.repositories.UserRepository;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.services.Impl.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RoleServiceTest {

    @Autowired
    private RoleService roleService;

    @MockBean
    private RoleRepository roleRepository;

    private Role role;

    @BeforeEach
    public void setUp() {
        role = new Role();
        role.setId(1L);
        role.setRoleName("Admin");
    }

    @Test
    public void testSaveRole() {
        when(roleRepository.save(any(Role.class))).thenReturn(role);

        Role savedRole = roleService.saveRole(role);

        assertThat(savedRole).isNotNull();
        assertThat(savedRole.getRoleName()).isEqualTo("Admin");

        verify(roleRepository, times(1)).save(any(Role.class));
    }

    @Test
    public void testFindRoleByRoleName() {
        String roleName = "Admin";
        when(roleRepository.findByRoleName(roleName)).thenReturn(role);

        Role foundRole = roleService.findRoleByRoleName(roleName);

        assertThat(foundRole).isNotNull();
        assertThat(foundRole.getRoleName()).isEqualTo(roleName);

        verify(roleRepository, times(1)).findByRoleName(roleName);
    }

    @Test
    public void testGetAllRoles() {
        Role role2 = new Role();
        role2.setId(2L);
        role2.setRoleName("Manager");

        when(roleRepository.findAll()).thenReturn(Arrays.asList(role, role2));

        List<Role> roles = roleService.getAllRoles();

        assertThat(roles).hasSize(2);
        assertThat(roles.get(0).getRoleName()).isEqualTo("Admin");
        assertThat(roles.get(1).getRoleName()).isEqualTo("Manager");

        verify(roleRepository, times(1)).findAll();
    }
}

