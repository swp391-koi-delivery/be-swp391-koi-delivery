package com.SWP391.KoiXpress.Model.Response;

import lombok.Data;

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
