package com.SWP391.KoiXpress.Model.request;

import com.SWP391.KoiXpress.Entity.BoxDetail;
import lombok.Data;

@Data
public class OrderDetailRequest {
    String type;
    int quantity;
    double size;
}
