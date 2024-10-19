package com.SWP391.KoiXpress.Model.response.Progress;

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

import java.util.Date;

@Data
public class DeleteProgressResponse {
    long id;
    String image;
    int totalBox;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    Date dateProgress;
    boolean isInProgress;
    HealthFishStatus healthFishStatus;
    ProgressStatus progressStatus;
    WareHouse wareHouse;
    Order order;
}
