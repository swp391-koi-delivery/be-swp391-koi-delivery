package com.SWP391.KoiXpress.Api;


import com.SWP391.KoiXpress.Service.OrderService;
import com.SWP391.KoiXpress.Service.VehicleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/delivery")
@CrossOrigin("*")
@SecurityRequirement(name="api")
public class DeliveringStaffAPI {

    @Autowired
    VehicleService vehicleService;

    @Autowired
    OrderService orderService;


}
