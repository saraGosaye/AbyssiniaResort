package com.saraWoldegiorgis.AbyssiniaHotelBookingApp;

import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.models.Booking;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.models.User;
import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveUserTest(){
        User user = User.builder()
                .firstName("Abebe")
                .lastName("Kebede")
                .userName("Abe")
                .email("abe@gmail.com")
                .password("1234")
                .build();
        userRepository.save(user);
        assertThat(user.getId()).isGreaterThan(1);
        assertThat(user.getFirstName()).isEqualTo("Abebe");
        assertThat(user.getEmail()).isEqualTo("abe@gmail.com");
    }
    @Test
    public void testFindByEmail() {
        // findByEmail returns a single User object so
        User user = userRepository.findByEmail("miki@yahoo.com");

        assertThat(user).isNotNull();

        // Assert that the user's email matches the expected value
        assertThat(user.getEmail()).isEqualTo("miki@yahoo.com");
    }
    @Test
    public void testFindUserById() {
        User user = userRepository.findById(1L).orElse(null);
        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(1L);
    }


}
