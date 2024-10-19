package com.SWP391.KoiXpress.Model.response.Box;

import com.SWP391.KoiXpress.Entity.Box;
import com.SWP391.KoiXpress.Entity.OrderDetail;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AllBoxDetailResponse {
    long id;
    int quantity;
    OrderDetail orderDetail;
    Box box;
}
