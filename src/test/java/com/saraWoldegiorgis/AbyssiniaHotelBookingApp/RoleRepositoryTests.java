package com.saraWoldegiorgis.AbyssiniaHotelBookingApp;

import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.models.Role;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.repositories.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class RoleRepositoryTests {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void saveRoleTest() {
        // Create a new Role object using the constructor
        Role role = new Role("Manager");

        // Save the role using the repository
        roleRepository.save(role);

        // Assert that the role has been saved and the ID is greater than 0
        assertThat(role.getId()).isGreaterThan(0);
    }
    @Test
    public void updateRoleTest() {

        // Retrieve the Role object by ID
        Role role = roleRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException("Role not found"));

        // Update the role name
        role.setRoleName("Supervisor");

        // Save the updated role
        Role updatedRole = roleRepository.save(role);

        // Assert that the role name has been updated
        assertThat(updatedRole.getRoleName()).isEqualTo("Supervisor");
    }
    @Test
    public void getListOfRolesTest(){
        List<Role> roles = roleRepository.findAll();
        assertThat(roles.size()).isGreaterThan(0);

    }
    @Test
    public void deleteRoleTest() {
        // Ensure a role with this ID exists
        Role role = roleRepository.findById(2L).orElse(null);
        if (role != null) {
            roleRepository.delete(role);
        }

        // Retrieve bookings by guest email
        Role roles = roleRepository.findByRoleName("Supervisor");

        // Assert that the list is empty
        assertThat(roles).isNull();
    }




}
