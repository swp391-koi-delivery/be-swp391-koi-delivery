package com.SWP391.KoiXpress.Model.response;

import com.SWP391.KoiXpress.Entity.Enum.DescribeOrder;
import com.SWP391.KoiXpress.Entity.Enum.HealthFishStatus;
import com.SWP391.KoiXpress.Entity.Enum.OrderStatus;
import com.SWP391.KoiXpress.Entity.Enum.PaymentStatus;
import com.SWP391.KoiXpress.Entity.Order;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class OrderResponse {

    long orderId;

    DescribeOrder describeOrder;

    Date orderDate;

    String originLocation;

    String destinationLocation;

    double totalPrice;

    String payment;

    double VAT;

    double size;

    int quantity;

    OrderStatus orderStatus;

    PaymentStatus paymentStatus;

    HealthFishStatus healthFishStatus;

    BoxDetailResponse boxDetail;
}


