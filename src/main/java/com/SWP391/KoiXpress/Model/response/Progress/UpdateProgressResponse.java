package com.SWP391.KoiXpress.Model.response.Progress;

import com.SWP391.KoiXpress.Entity.Enum.HealthFishStatus;
import com.SWP391.KoiXpress.Entity.Enum.ProgressStatus;
import com.SWP391.KoiXpress.Entity.Orders;
import com.SWP391.KoiXpress.Entity.WareHouses;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class UpdateProgressResponse {
    long id;
    String image;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    Date dateProgress;
    boolean isInProgress;
    HealthFishStatus healthFishStatus;
    ProgressStatus progressStatus;
    WareHouses wareHouses;
    Orders orders;
}
