package com.SWP391.KoiXpress.Model.response;

import com.SWP391.KoiXpress.Entity.Enum.DescribeOrder;
import com.SWP391.KoiXpress.Entity.Enum.OrderStatus;
import com.SWP391.KoiXpress.Entity.Enum.PaymentStatus;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class OrderResponse {

    private long orderId;

    private DescribeOrder describeOrder;

    private Date orderDate;

    private String originLocation;

    private String destinationLocation;

    private double totalPrice;

    private String payment;

    private double VAT;

    private OrderStatus orderStatus;

    private PaymentStatus paymentStatus;

}
