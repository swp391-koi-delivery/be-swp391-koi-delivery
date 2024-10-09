package com.SWP391.KoiXpress.Entity;

import com.SWP391.KoiXpress.Entity.Enum.DescribeOrder;
import com.SWP391.KoiXpress.Entity.Enum.HealthFishStatus;
import com.SWP391.KoiXpress.Entity.Enum.OrderStatus;
import com.SWP391.KoiXpress.Entity.Enum.Payment;
import com.SWP391.KoiXpress.Entity.Enum.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name="`order`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long orderId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    UUID trackingOrder = UUID.randomUUID();

    Date orderDate;

    @NotBlank(message = "location start can not be blank")
    String originLocation;

    @NotBlank(message = "location end can not be blank")
    String destinationLocation;

    @NotNull(message = "Size cannot be null")
    double size;

    @Min(value = 1, message = "Quantity must be at least 1")
    int quantity;


    @Min(value = 0,message = "price at least 0")
    @NotNull(message = "price can not be null")
    double price;

    int totalquantity;

    @Enumerated(EnumType.STRING)
    DescribeOrder describeOrder;

    @Enumerated(EnumType.STRING)
    Payment payment;

    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    PaymentStatus paymentStatus;


    @Enumerated(EnumType.STRING)
    HealthFishStatus healthFishStatus;

    @ManyToOne
    @JoinColumn(name="user_id")
    User user;

//    @ManyToOne
//    @JoinColumn(name = "report_id")
//    Report report;

    @OneToMany(mappedBy = "order")
    List<OrderDetail> orderDetails;

//    @OneToMany(mappedBy = "order")
//    @JsonIgnore
//    List<Progress> progresses;
//
//    @OneToOne
//    @JoinColumn(name = "invoice_id")
//    Invoice invoice;

}
