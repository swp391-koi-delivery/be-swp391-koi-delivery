package com.SWP391.KoiXpress.Model.request;

import com.SWP391.KoiXpress.Entity.Enum.MethodTransPort;
import com.SWP391.KoiXpress.Entity.Enum.PaymentMethod;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrderRequestCustomer {
    @NotBlank(message = "location start can not be blank")
    String originLocation;

    @NotBlank(message = "location end can not be blank")
    String destinationLocation;

    String customerNotes;

    @Enumerated(EnumType.STRING)
    PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    MethodTransPort methodTransPort;
}
