package com.SWP391.KoiXpress.Model.request;

import com.SWP391.KoiXpress.Entity.Enum.Role;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterRequestManager {
    @NotBlank(message = "username can not be blank!")
    @Size(min = 6, message = "username must at least 6 character")
    String username;

    Role role;
    @NotBlank(message = "password can not be blank!")
    @Pattern(regexp = ".*[A-Z].*", message = "Password must contain at least one uppercase letter.")
    @Size(min=6, message = "password at least 6 character!")
    String password;

    @NotBlank(message = "fullname can not be blank!")
    @Size(min = 1, message = "fullName at least 1 character!")
    String fullname;

    @Column(length = 200)
    String image;

    @Column(length = 200)
    String address;

    @NotBlank(message = "phone can not be blank!")
    @Pattern(regexp = "(84|0[3|5|7|8|9])+(\\d{8})", message = "Invalid phone!")
    @Column(unique = true)
    String phone;

    @NotBlank(message = "email can not be blank!")
    @Email(message = "Email not valid")
    @Column(unique = true)
    String email;

    @Min(value = 0, message = "at least 0")
    long loyaltyPoint;

    boolean isDeleted;
}
