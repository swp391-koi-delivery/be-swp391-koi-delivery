package com.SWP391.KoiXpress.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.NumberFormat;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BoxDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    int largeBox;
    int mediumBox;
    int smallBox;
    int totalBox;
    @NumberFormat(pattern = "#.##")
    double totalPrice;
    @NumberFormat(pattern = "#.##")
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
