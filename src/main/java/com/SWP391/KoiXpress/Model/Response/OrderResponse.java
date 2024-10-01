package com.SWP391.KoiXpress.Model.Response;

import lombok.Data;

import java.sql.Date;

@Data
public class OrderResponse {
    long orderId;
    String describeOrder;
    Date orderDate;
    String originLocation;
    String destinationLocation;
    double totalPrice;
    String payment;
    String orderStatus;
    String paymentStatus;
}
