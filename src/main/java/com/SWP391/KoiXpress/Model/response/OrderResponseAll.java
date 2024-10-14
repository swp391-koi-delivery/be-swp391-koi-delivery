package com.SWP391.KoiXpress.Model.response;

import com.SWP391.KoiXpress.Entity.Enum.OrderStatus;
import com.SWP391.KoiXpress.Entity.Enum.PaymentMethod;
import com.SWP391.KoiXpress.Entity.OrderDetail;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class OrderResponseAll {
    long id;
    UUID trackingOrder = UUID.randomUUID();
    Date orderDate;
    Date deliveryDate;
    String originLocation;
    String destinationLocation;
    double totalPrice;
    int totalQuantity;
    int totalBox;
    double totalVolume;
    String customerNotes;
    PaymentMethod paymentMethod;
    OrderStatus orderStatus;
    UserResponse userResponse;

    List<OrderDetail> orderDetails;
}
