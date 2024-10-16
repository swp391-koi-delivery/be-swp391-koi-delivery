package com.SWP391.KoiXpress.Entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.NumberFormat;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BoxDetailResult {
    List<BoxDetail> boxDetails;
    @NumberFormat(pattern = "#.##")
    double totalPrice;
    @NumberFormat(pattern = "#.##")
    double totalVolume;
    int totalCount;

}
