package com.SWP391.KoiXpress.Model.response;

import com.SWP391.KoiXpress.Entity.Enum.HealthFishStatus;
import com.SWP391.KoiXpress.Entity.Enum.ProgressStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import java.util.Date;

@Data
public class ProgressResponse {
    long id;
    String image;
    Date dateProgress;
    double totalVolume;
    int totalBox;
    boolean isInProgress = false;
    HealthFishStatus healthFishStatus;
    ProgressStatus progressStatus;

}
