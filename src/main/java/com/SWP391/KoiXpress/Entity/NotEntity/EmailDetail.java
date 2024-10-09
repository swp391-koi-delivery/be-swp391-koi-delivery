package com.SWP391.KoiXpress.Entity.NotEntity;

import com.SWP391.KoiXpress.Entity.User;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailDetail {
    User user;
    String subject;
    String link;
}
