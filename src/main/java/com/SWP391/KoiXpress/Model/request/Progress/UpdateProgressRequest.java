package com.SWP391.KoiXpress.Model.request.Progress;

import com.SWP391.KoiXpress.Entity.Enum.HealthFishStatus;
import com.SWP391.KoiXpress.Entity.Enum.ProgressStatus;
import com.SWP391.KoiXpress.Entity.Order;
import com.SWP391.KoiXpress.Entity.WareHouse;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import java.util.Date;

@Data
public class UpdateProgressRequest {

    String image;

    @Enumerated(EnumType.STRING)
    HealthFishStatus healthFishStatus;

    @Enumerated(EnumType.STRING)
    ProgressStatus progressStatus;

}
