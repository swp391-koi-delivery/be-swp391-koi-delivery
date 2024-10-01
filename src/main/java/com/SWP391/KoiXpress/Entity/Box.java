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

    @NotBlank(message = "Box name can not be blank")
    String name;

    @Min(value = 0,message = "Volume at least 0")
    long volume;

}
