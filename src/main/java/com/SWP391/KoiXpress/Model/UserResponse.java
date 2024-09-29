package com.SWP391.KoiXpress.Model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserResponse {
    long userId;
    String username;
    String fullname;
    String image;
    String address;
    String phone;
    String email;
    long loyaltyPoint;
    Boolean userstatus;
}
