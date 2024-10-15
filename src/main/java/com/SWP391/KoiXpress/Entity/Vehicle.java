package com.SWP391.KoiXpress.Entity;

import com.SWP391.KoiXpress.Entity.Enum.VehicleType;
import com.SWP391.KoiXpress.Model.response.OrderResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.NumberFormat;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID licensePlate;

    String driverName;
    String driverPhone;

    @Enumerated(EnumType.STRING)
    VehicleType vehicleType;

    int volume;

    boolean isAvailable = true;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    @JsonIgnore
    List<Progress> progresses;
    
}
