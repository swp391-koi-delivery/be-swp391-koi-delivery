package com.SWP391.KoiXpress.Entity;

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
@Table(name = "`delivery_method`")
public class DeliveryMethods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String typeVehicle;

    @NumberFormat(pattern = "#.##")
    double price;
}
