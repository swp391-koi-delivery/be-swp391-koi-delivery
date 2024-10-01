package com.SWP391.KoiXpress.Model.Request;


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
