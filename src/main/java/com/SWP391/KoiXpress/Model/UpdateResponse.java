package com.SWP391.KoiXpress.Model;

import lombok.Data;

@Data
public class UpdateResponse {
    String role;
    String username;
    String password;
    String fullname;
    String image;
    String address;
    String phone;
    String email;
    long loyaltyPoint;
}
