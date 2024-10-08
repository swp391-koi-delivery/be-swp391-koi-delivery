package com.SWP391.KoiXpress.Model.response;

import lombok.Data;

@Data
public class BoxDetailResponse {

    long boxId;

    int largeBox;

    int mediumBox;

    int smallBox;

    int totalBox;

    double totalPrice;

    double totalVolume;
}

