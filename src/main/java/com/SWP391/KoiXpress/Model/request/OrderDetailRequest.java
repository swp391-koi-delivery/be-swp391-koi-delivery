package com.SWP391.KoiXpress.Model.request;

import com.SWP391.KoiXpress.Entity.Enum.DescribeOrder;
import com.SWP391.KoiXpress.Entity.Enum.HealthFishStatus;
import com.SWP391.KoiXpress.Entity.Enum.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;


@Data
public class OrderDetailRequest {
    @NumberFormat(pattern = "#.##")
    double priceOfFish;

    @NotBlank(message = "nameFarm can not blank")
    String nameFarm;

    @NotBlank(message = "farmAddress can not blank")
    String farmAddress;

    @NotBlank(message = "origin of fish should not null")
    String origin;

    @NotBlank(message = "need information of recipient")
    String recipientInfo;

    @NotBlank(message = "fishSpecies can not blank")
    String fishSpecies;

    @Min(value = 1)
    @Max(value = 100)
    int numberOfFish;

    @NumberFormat(pattern = "#.##")
    double sizeOfFish;

    @Enumerated(EnumType.STRING)
    DescribeOrder describeOrder;

}
