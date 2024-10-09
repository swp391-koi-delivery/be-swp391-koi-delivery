package com.SWP391.KoiXpress.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class BoxDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long boxId;
    int largeBox;
    int mediumBox;
    int smallBox;
    int totalBox;
    double price;
    double totalVolume;


    @OneToOne(mappedBy = "boxDetail")
    @JsonIgnore
    OrderDetail orderDetail;

    public void setLargeBox(int largeBox) {
        this.largeBox = largeBox;
        updateTotalBox();
    }


    public void setMediumBox(int mediumBox) {
        this.mediumBox = mediumBox;
        updateTotalBox();
    }


    public void setSmallBox(int smallBox) {
        this.smallBox = smallBox;
        updateTotalBox();
    }


    private void updateTotalBox() {
        this.totalBox = this.largeBox + this.mediumBox + this.smallBox;
    }
}
