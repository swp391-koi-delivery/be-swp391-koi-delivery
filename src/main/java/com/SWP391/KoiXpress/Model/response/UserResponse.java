package com.SWP391.KoiXpress.Model.response;

import com.SWP391.KoiXpress.Entity.Enum.Role;
import lombok.Data;

@Data
public class UserResponse {
    long userId;
    Role role;
    String username;
}