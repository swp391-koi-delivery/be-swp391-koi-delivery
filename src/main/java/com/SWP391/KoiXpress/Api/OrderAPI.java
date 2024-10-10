package com.SWP391.KoiXpress.Api;

import com.SWP391.KoiXpress.Entity.Order;
import com.SWP391.KoiXpress.Model.request.OrderRequest;
import com.SWP391.KoiXpress.Model.response.OrderResponse;
import com.SWP391.KoiXpress.Service.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@SecurityRequirement(name="api")
public class OrderAPI {

    @Autowired
    OrderService orderService;


    @PostMapping
    public ResponseEntity create(@Valid @RequestBody OrderRequest orderRequest){
        OrderResponse order = orderService.create(orderRequest);
        return ResponseEntity.ok(order);
    }
    @GetMapping
    public ResponseEntity get(){
        List<OrderResponse> orderResponseList = orderService.getAllOrders();
        return ResponseEntity.ok(orderResponseList);
    }

    @GetMapping("{orderId}")
    public ResponseEntity getEachOrder(long id){
        OrderResponse orderResponse = orderService.getEachOrderById(id);
        return ResponseEntity.ok(orderResponse);
    }
}
