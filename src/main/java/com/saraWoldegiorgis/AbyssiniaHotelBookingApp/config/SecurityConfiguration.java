package com.saraWoldegiorgis.AbyssiniaHotelBookingApp.config;

import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.services.Impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    private UserService userDetailsService;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService); //set the custom user details service
        auth.setPasswordEncoder(passwordEncoder()); //set the password encoder - bcrypt
        return auth;
    }
    //beans
    //bcrypt bean definition
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }


    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(
                        (auth) -> auth
                                .requestMatchers("/", "/login*",
                                        "/css/*", "/img/**", "/js/*", "/sign-up", "/signup" +
                                                "-process").permitAll() //Specifies which URLs are accessible without authentication
                                .requestMatchers("/home").authenticated() // Specifies which URLs
                                // require authentication
//                                .requestMatchers("/home").hasAnyRole("USER", "ADMIN")
                                .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login") // Sets a custom login page (/login)
                        .loginProcessingUrl("/login") // Specifies where the login form should be submitted (/login).
//                        .successForwardUrl("/home")
                        .defaultSuccessUrl("/home", true)// Redirects to /home on successful login.
                        .permitAll()
                )
                .logout(
                        logout -> logout //  Configures the logout process to invalidate the session and clear authentication.
                                .invalidateHttpSession(true)
                                .clearAuthentication(true)
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                );
        return http.build();
    }
}
