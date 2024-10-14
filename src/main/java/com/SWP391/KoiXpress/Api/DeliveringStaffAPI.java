package com.SWP391.KoiXpress.Api;


import com.SWP391.KoiXpress.Entity.Progress;
import com.SWP391.KoiXpress.Model.request.VehicleRequest;
import com.SWP391.KoiXpress.Service.OrderService;
import com.SWP391.KoiXpress.Service.VehicleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/delivery")
@CrossOrigin("*")
@SecurityRequirement(name="api")
public class DeliveringStaffAPI {

    @Autowired
    VehicleService vehicleService;

    @Autowired
    OrderService orderService;

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody VehicleRequest vehicleRequest){
        List<Progress> progresses = vehicleService.createVehicle(vehicleRequest);
        return ResponseEntity.ok(progresses);
    }
}
