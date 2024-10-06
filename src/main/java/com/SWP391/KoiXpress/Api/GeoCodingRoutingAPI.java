package com.SWP391.KoiXpress.Api;

import com.SWP391.KoiXpress.Service.GeoCodingService;
import com.SWP391.KoiXpress.Service.RoutingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class GeoCodingRoutingAPI {
    @Autowired
    GeoCodingService geoCodingService;

    @Autowired
    RoutingService routingService;

    @GetMapping("/route")
    public String route(@RequestParam String startLocation, @RequestParam String endLocation) {
        try {
            double[] startCoords = geoCodingService.geocoding(startLocation);
            double[] endCoords = geoCodingService.geocoding(endLocation);
            return routingService.getFormattedRoute(startCoords[0], startCoords[1], endCoords[0], endCoords[1]); // Use getFormattedRoute here
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

}
