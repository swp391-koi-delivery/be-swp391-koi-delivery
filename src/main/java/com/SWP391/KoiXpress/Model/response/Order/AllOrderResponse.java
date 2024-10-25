package com.SWP391.KoiXpress.Model.response.Order;

import com.SWP391.KoiXpress.Entity.Enum.OrderStatus;
import com.SWP391.KoiXpress.Entity.OrderDetails;
import com.SWP391.KoiXpress.Model.response.User.UserResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class AllOrderResponse {
    long id;
    UUID trackingOrder = UUID.randomUUID();
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    Date orderDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    Date deliveryDate;
    String originLocation;
    String destinationLocation;
    double totalPrice;
    int totalQuantity;
    int totalBox;
    double totalVolume;
    String recipientInfo;
    String customerNotes;
    OrderStatus orderStatus;
    UserResponse eachUserResponse;

    List<OrderDetails> orderDetails;
}
