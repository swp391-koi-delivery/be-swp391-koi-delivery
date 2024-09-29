package com.SWP391.KoiXpress.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Date;
@Data
@Entity
@Table(name="`order`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long orderId;

    @NotBlank(message = "mo ta don hang(si, le)")
    String describeOrder;

    @NotNull(message = "date can not be null")
    @Temporal(TemporalType.DATE)
    Date orderDate;

    @NotBlank(message = "location start can not be blank")
    String originLocation;

    @NotBlank(message = "location end can not be blank")
    String destinationLocation;

    @Min(value = 0,message = "price at least 0")
    @NotNull(message = "price can not be null")
    double totalPrice;
    String payment;

    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    PaymentStatus paymentStatus;
}
