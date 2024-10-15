package com.SWP391.KoiXpress.Model.request;

import com.SWP391.KoiXpress.Entity.Enum.VehicleType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;


@Data
public class VehicleRequest {
    long idDriver;
    @Enumerated(EnumType.STRING)
    VehicleType vehicleType;
}
