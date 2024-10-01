package com.SWP391.KoiXpress.Service;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class RouteStop {
    private String location;
    private double load;

    public RouteStop(String location, double load) {
        this.location = location;
        this.load = load;
    }
}