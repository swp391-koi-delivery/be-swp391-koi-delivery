package com.SWP391.KoiXpress.Model.request;

import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

@Data
public class BoxRequest {
    String type;

    @NumberFormat(pattern = "#.##")
    double volume;

    @NumberFormat(pattern = "#.##")
    double price;
}
