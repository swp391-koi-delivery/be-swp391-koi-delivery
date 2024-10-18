package com.SWP391.KoiXpress.Model.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginGoogleResponse {
        String token;
        boolean profileIncomplete;
        private String username;
        private String password;

}
