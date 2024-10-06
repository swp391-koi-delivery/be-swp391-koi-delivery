package com.SWP391.KoiXpress.Entity;

import com.SWP391.KoiXpress.Entity.Enum.DescribeOrder;
import com.SWP391.KoiXpress.Entity.Enum.OrderStatus;
import com.SWP391.KoiXpress.Entity.Enum.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
@Entity
@Table(name="`order`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long orderId;

    boolean isDeleted = false;

    @Enumerated(EnumType.STRING)
    DescribeOrder describeOrder;

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

    double VAT;

    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    PaymentStatus paymentStatus;

    @ManyToOne
    @JoinColumn(name="user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "report_id")
    Report report;

    @OneToMany(mappedBy = "order")
    @JsonIgnore
    List<Progress> progresses;

    @OneToOne
    @JoinColumn(name = "invoice_id")
    Invoice invoice;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "box_detail_id", referencedColumnName = "boxId")
    BoxDetail boxDetail;

}
