package com.SWP391.KoiXpress.Model.request.Authen;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {
    //yeu cau dang nhap
    @NotBlank(message = "username can not be blank!")
    @Size(min = 6, message = "username must at least 6 character")
    String username;

    @NotBlank(message = "password can not be blank!")
    @Pattern(regexp = ".*[A-Z].*", message = "Password must contain at least one uppercase letter.")
    @Size(min=6, message = "password at least 6 character!")
    String password;
}
