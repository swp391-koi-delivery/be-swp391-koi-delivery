package com.SWP391.KoiXpress.Model.request;

import com.SWP391.KoiXpress.Entity.Enum.DescribeOrder;
import com.SWP391.KoiXpress.Entity.Enum.PaymentStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Date;

@Data
public class OrderRequest {


    @NotNull(message = "Describe order can not be null")
    DescribeOrder describeOrder;

    @NotNull(message = "Date can not be null")
    Date orderDate;

    @NotBlank(message = "Origin location can not be blank")
    String originLocation;

    @NotBlank(message = "Destination location can not be blank")
    String destinationLocation;

    @NotNull(message = "Size cannot be null")  // Thay đổi từ String sang Double nếu là số
    @Min(value = 20, message = "Size must be at least 19.0")
    double size;

    @Min(value = 1, message = "Quantity must be at least 1")
    int quantity;


    @NotBlank(message = "Payment cannot be null")
    String payment;

    @Min(value = 0, message = "VAT must be at least 0")  // Thêm ràng buộc tối thiểu cho VAT
    double VAT;

    PaymentStatus paymentStatus;
}
