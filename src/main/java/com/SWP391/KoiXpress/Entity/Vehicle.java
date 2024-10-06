package com.SWP391.KoiXpress.Entity;

import com.SWP391.KoiXpress.Entity.NotEntity.RouteStop;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
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
    double remainingCapacity;

    @OneToMany(mappedBy = "vehicle")
    @JsonIgnore
    List<ProgressDetail> progressDetails;
    @ElementCollection  // Declare the collection of embeddable RouteStop objects
    private List<RouteStop> stops = new ArrayList<>();

    public void addStop(RouteStop stop) {
        if (this.remainingCapacity >= stop.getLoad()) {
            stops.add(stop);  // Add the RouteStop object
            this.remainingCapacity -= stop.getLoad();  // Reduce the remaining capacity
        } else {
            throw new IllegalArgumentException("Not enough capacity to add this stop.");
        }
    }
}
