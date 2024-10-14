package com.SWP391.KoiXpress.Model.response;


import com.SWP391.KoiXpress.Entity.Enum.DescribeOrder;
import com.SWP391.KoiXpress.Entity.Enum.OrderStatus;
import com.SWP391.KoiXpress.Entity.Enum.PaymentMethod;
import com.SWP391.KoiXpress.Entity.OrderDetail;
import com.fasterxml.jackson.annotation.JsonFormat;
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

    List<OrderDetail> orderDetails;
}
