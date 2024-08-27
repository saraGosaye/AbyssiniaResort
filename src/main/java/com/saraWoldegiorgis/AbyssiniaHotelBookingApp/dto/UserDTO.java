package com.saraWoldegiorgis.AbyssiniaHotelBookingApp.dto;

import com.saraWoldegiorgis.AbyssiniaHotelBookingApp.config.FieldMatch;
import jakarta.persistence.Column;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@FieldMatch.List({
        @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match"),
        @FieldMatch(first = "email", second = "confirmEmail", message = "The email fields must match")
})
public class UserDTO {

    @NotEmpty
    private String userName;

    @Pattern(regexp = "[A-Za-z]+$", message = "Only alphabetic allowed")
    @NotEmpty
    private String firstName;

    @Pattern(regexp = "[A-Za-z]+$", message = "Only alphabetic allowed")
    @NotEmpty
    private String lastName;

    @Column(unique = true)
    @Email
    @NotEmpty
    private String email;

    @NotEmpty(message = "Required")
    private String password;

    @NotEmpty(message = "Required")
    private String matchingPassword;

    @AssertTrue
    private Boolean terms;

    public UserDTO(@NotEmpty String userName, @Pattern(regexp = "[A-Za-z]+$", message = "Only alphabetic allowed") String firstName, @Pattern(regexp = "[A-Za-z]+$", message = "Only alphabetic allowed") String lastName, @Email String email, String phone, @Pattern(regexp = "[0-9]{5}$", message = "Zip code wrong format") String zip, @NotEmpty(message = "Required") String password, @NotEmpty(message = "Required") String matchingPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.matchingPassword = matchingPassword;
    }


}
