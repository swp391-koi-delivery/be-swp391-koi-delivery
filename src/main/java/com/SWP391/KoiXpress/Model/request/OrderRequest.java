package com.SWP391.KoiXpress.Model.request;


import com.SWP391.KoiXpress.Entity.Enum.DescribeOrder;
import com.SWP391.KoiXpress.Entity.Enum.OrderStatus;
import com.SWP391.KoiXpress.Entity.Enum.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


import java.util.Date;
import java.util.List;

@Data
public class OrderRequest {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    Date orderDate;

    @NotBlank(message = "location start can not be blank")
    String originLocation;

    @NotBlank(message = "location end can not be blank")
    String destinationLocation;

    @Min(value = 0,message = "totalPrice at least 0")
    @NotNull(message = "totalPrice can not be null")
    double totalPrice;

    int totalQuantity;

    double totalVolume;

    String customerNotes;


    @Enumerated(EnumType.STRING)
    PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;

    List<OrderDetailRequest> orderDetailRequestList;

}
