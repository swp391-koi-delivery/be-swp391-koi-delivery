package com.SWP391.KoiXpress.Model.response;

import com.SWP391.KoiXpress.Entity.Enum.EmailStatus;
import lombok.Data;

@Data
public class RegisterResponse {
    long userId;
    String username;
    String fullname;
    String password;
    String image;
    String address;
    String phone;
    String email;
    EmailStatus emailStatus;
    Boolean userstatus;
    String role;
    long loyaltyPoint;

}
