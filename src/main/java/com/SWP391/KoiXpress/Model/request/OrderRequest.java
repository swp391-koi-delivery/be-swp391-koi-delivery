package com.SWP391.KoiXpress.Model.request;

import com.SWP391.KoiXpress.Entity.Enum.DescribeOrder;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Date;

@Data
public class OrderRequest {

    @NotNull(message = "Describe order can not be null")
    private DescribeOrder describeOrder;

    @NotNull(message = "Date can not be null")
    private Date orderDate;

    @NotBlank(message = "Origin location can not be blank")
    private String originLocation;

    @NotBlank(message = "Destination location can not be blank")
    private String destinationLocation;

    @Min(value = 0, message = "Price at least 0")
    @NotNull(message = "Price can not be null")
    private double totalPrice;

    private String payment;

    private double VAT;


}
