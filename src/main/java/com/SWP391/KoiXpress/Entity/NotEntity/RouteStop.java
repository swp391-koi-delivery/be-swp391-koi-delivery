package com.SWP391.KoiXpress.Entity.NotEntity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable  // Make it embeddable so that it can be used as part of another entity
public class RouteStop {
    private String location;
    private double load;

    public RouteStop(String location, double load) {
        this.location = location;
        this.load = load;
    }

    public RouteStop() {
        // Default constructor
    }
}
