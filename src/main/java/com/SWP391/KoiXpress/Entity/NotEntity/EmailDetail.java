package com.SWP391.KoiXpress.Entity.NotEntity;

import com.SWP391.KoiXpress.Entity.User;
import lombok.Data;

@Data
public class EmailDetail {
    User user;
    String subject;
    String link;
}
