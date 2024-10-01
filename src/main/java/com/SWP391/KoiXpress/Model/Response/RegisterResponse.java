package com.SWP391.KoiXpress.Model.Response;

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
    String role;
    long loyaltyPoint;
}
