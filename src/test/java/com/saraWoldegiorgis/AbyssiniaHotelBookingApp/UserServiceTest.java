package com.saraWoldegiorgis.AbyssiniaHotelBookingApp;

import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.dto.UserDTO;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.models.Role;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.models.User;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.repositories.UserRepository;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.services.Impl.RoleService;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.services.Impl.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleService roleService;

    @MockBean
    private BCryptPasswordEncoder encoder;

    private User user;
    private Role guestRole;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("password123");

        guestRole = new Role();
        guestRole.setId(2L);
        guestRole.setRoleName("GUEST");
    }

    @ParameterizedTest
    @ValueSource(strings = {"test1@example.com", "test2@example.com", "test3@example.com"})
    public void testFindUserByEmail(String email) {
        User user = new User();
        user.setEmail(email);
        user.setPassword("password123");

        when(userRepository.findByEmail(email)).thenReturn(user);

        User foundUser = userService.findUserByEmail(email);

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo(email);
    }

    @Test
    public void testCreatUser() {
        when(roleService.findRoleByRoleName(anyString())).thenReturn(guestRole);
        when(encoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("newuser@example.com");
        userDTO.setPassword("newpassword");

        userService.creat(userDTO);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo(userDTO.getEmail());
        assertThat(savedUser.getPassword()).isEqualTo("encodedPassword");
        assertThat(savedUser.getRoles()).contains(guestRole);

        verify(roleService, times(1)).findRoleByRoleName("GUEST");
        verify(encoder, times(1)).encode("newpassword");
    }
}
