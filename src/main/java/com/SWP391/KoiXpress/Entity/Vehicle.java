package com.SWP391.KoiXpress.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long vehicleId;

    String typeOfVehicle;
    int capacity;
    String currentLocation;
    double price;
    Boolean status;


    @OneToMany(mappedBy = "vehicle")
    @JsonIgnore
    List<ProgressDetail> progressDetails;
}
