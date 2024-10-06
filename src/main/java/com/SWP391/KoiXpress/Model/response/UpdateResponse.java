package com.SWP391.KoiXpress.Model.response;

import com.SWP391.KoiXpress.Entity.Enum.EmailStatus;
import com.SWP391.KoiXpress.Entity.Enum.Role;
import lombok.Data;

@Data
public class UpdateResponse {
    long userId;
    Role role;
    String username;
    String password;
    String fullname;
    String image;
    String address;
    String phone;
    String email;
    EmailStatus emailStatus;
    Boolean userstatus;
    long loyaltyPoint;
}
