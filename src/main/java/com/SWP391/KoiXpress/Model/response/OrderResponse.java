package com.SWP391.KoiXpress.Model.response;


import com.SWP391.KoiXpress.Entity.Enum.DescribeOrder;
import com.SWP391.KoiXpress.Entity.Enum.OrderStatus;
import com.SWP391.KoiXpress.Entity.Enum.Payment;
import com.SWP391.KoiXpress.Entity.Enum.PaymentStatus;
import com.SWP391.KoiXpress.Entity.OrderDetail;
import com.SWP391.KoiXpress.Entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Data
public class OrderResponse {
    long id;
    UUID trackingOrder = UUID.randomUUID();
    Date orderDate;
    String originLocation;
    String destinationLocation;

    @NumberFormat(pattern = "#.##")
    double price;

    int totalquantity;
    DescribeOrder describeOrder;
    Payment payment;
    OrderStatus orderStatus;
    PaymentStatus paymentStatus;
    UserResponse user;
    List<OrderDetail> orderDetails;
}
