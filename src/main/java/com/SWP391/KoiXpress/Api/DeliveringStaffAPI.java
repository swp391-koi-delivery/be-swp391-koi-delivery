package com.SWP391.KoiXpress.Api;

import com.SWP391.KoiXpress.Entity.Enum.OrderStatus;
import com.SWP391.KoiXpress.Entity.Order;
import com.SWP391.KoiXpress.Model.request.VehicleRequest;
import com.SWP391.KoiXpress.Model.response.OrderResponse;
import com.SWP391.KoiXpress.Service.OrderService;
import com.SWP391.KoiXpress.Service.VehicleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
        List<Order> orders = orderService.getAllOrders();
        List<OrderResponse> orderResponses = vehicleService.createVehicle(orders, vehicleRequest);
        return ResponseEntity.ok(orderResponses);
    }
}
