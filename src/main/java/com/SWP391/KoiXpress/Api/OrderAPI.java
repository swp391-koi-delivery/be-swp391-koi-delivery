package com.SWP391.KoiXpress.Api;

import com.SWP391.KoiXpress.Entity.Order;
import com.SWP391.KoiXpress.Model.request.OrderRequest;
import com.SWP391.KoiXpress.Service.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
@SecurityRequirement(name="api")
public class OrderAPI {

    @Autowired
    OrderService orderService;


    @PostMapping
    public ResponseEntity create(@RequestBody OrderRequest orderRequest){
        Order order = orderService.create(orderRequest);
        return ResponseEntity.ok(order);
    }
}
