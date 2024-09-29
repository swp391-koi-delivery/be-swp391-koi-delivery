package com.SWP391.KoiXpress.Model;


import com.SWP391.KoiXpress.Entity.Role;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    //yeu cau dang ky
    String username;
    String password;
    String fullname;
    String image;
    String address;
    String phone;
    String email;
}
