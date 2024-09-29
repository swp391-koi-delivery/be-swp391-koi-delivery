package com.SWP391.KoiXpress.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
public class Box {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long boxId;
    @NotBlank(message = "length can not blank")
    @Min(value = 0,message = "length at least 0!")
    long length;

    @NotBlank(message = "height can not blank")
    @Min(value = 0,message = "height at least 0!")
    long height;

    @NotBlank(message = "width can not blank")
    @Min(value = 0,message = "width at least 0!")
    long width;


    @Min(value=0, message = "quantity at least 0")
    int quantity;

    @Min(value = 0,message = "Volume at least 0")
    long volume = length*height*width;

    @Min(value = 0, message = "Price at least 0 ")
    double price;
}
