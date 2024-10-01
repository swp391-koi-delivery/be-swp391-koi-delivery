package com.SWP391.KoiXpress.Entity;

import com.SWP391.KoiXpress.Service.RouteStop;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Vehicle {
    private double totalCapacity;
    private double remainingCapacity;
    private List<RouteStop> stops = new ArrayList<>(); // Initialize stops to avoid null pointer issues
    private String currentLocation;  // To track the truck's current location

    // Constructor with parameters to initialize truck with capacity and starting location
    public Vehicle(double totalCapacity, String startingLocation) {
        this.totalCapacity = totalCapacity;
        this.remainingCapacity = totalCapacity;  // Initialize remaining capacity
        this.stops = new ArrayList<>();  // Ensure the stops list is initialized
        this.currentLocation = startingLocation;  // Initialize starting location
    }

    // Default constructor
    public Vehicle() {
        this.stops = new ArrayList<>();  // Ensure the stops list is initialized
    }

    public void addStop(RouteStop stop) {
        if (this.remainingCapacity + stop.getLoad() <= this.totalCapacity) {
            stops.add(stop);
            // Sau khi giao hàng, tải trọng còn lại phải tăng lên vì xe đã dỡ bớt hàng
            this.remainingCapacity += stop.getLoad();
        } else {
            throw new IllegalArgumentException("Not enough capacity to add this stop.");
        }
    }


}
