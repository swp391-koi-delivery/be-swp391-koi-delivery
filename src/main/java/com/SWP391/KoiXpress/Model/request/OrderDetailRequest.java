package com.SWP391.KoiXpress.Model.request;

import com.SWP391.KoiXpress.Entity.Enum.HealthFishStatus;
import com.SWP391.KoiXpress.Entity.Enum.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import java.util.Date;

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

    @NotBlank(message = "destination not null")
    String destination;

    @NotBlank(message = "need information of recipient")
    String recipientInfo;

    @NotBlank(message = "fishSpecies can not blank")
    String fishSpecies;

    @Min(value = 1)
    @Max(value = 100)
    int numberOfFish;

    int totalBox;
    @NumberFormat(pattern = "#.##")
    double sizeOfFish;

    @NumberFormat(pattern = "#.##")
    double totalVolume;

    @Enumerated(EnumType.STRING)
    HealthFishStatus healthFishStatus;

    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;
}
