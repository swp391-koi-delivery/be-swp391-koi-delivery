package com.SWP391.KoiXpress.Model.Request;

import lombok.Data;

import java.sql.Date;

@Data
public class OrderRequest {
    String describeOrder;
    Date orderDate;
    String originLocation;
    String destinationLocation;
    double totalPrice;
    String payment;
    String orderStatus;
    String paymentStatus;
}
