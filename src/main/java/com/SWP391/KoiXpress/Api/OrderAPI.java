package com.SWP391.KoiXpress.Api;


import com.SWP391.KoiXpress.Model.request.Order.CreateOrderRequest;
import com.SWP391.KoiXpress.Model.request.Order.UpdateOrderRequest;
import com.SWP391.KoiXpress.Model.response.Order.*;
import com.SWP391.KoiXpress.Model.request.OrderRequest;
import com.SWP391.KoiXpress.Model.response.OrderResponse;
import com.SWP391.KoiXpress.Model.response.OrderResponseAll;
import com.SWP391.KoiXpress.Model.request.OrderRequestCustomer;
import com.SWP391.KoiXpress.Model.response.OrderResponse;
import com.SWP391.KoiXpress.Model.response.OrderResponseAll;
import com.SWP391.KoiXpress.Service.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@SecurityRequirement(name = "api")
@CrossOrigin("*")
@PreAuthorize("hasAuthority('MANAGER') or hasAuthority('DELIVERING_STAFF') or hasAuthority('SALE_STAFF')")
public class OrderAPI {

    @Autowired
    OrderService orderService;


    @PostMapping
    public ResponseEntity create(@Valid @RequestBody OrderRequest orderRequest) throws Exception {
        OrderResponse order = orderService.create(orderRequest);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/each-user")
    public ResponseEntity get() {
        List<OrderResponse> orderResponseList = orderService.getAllOrdersByCurrentUser();
        return ResponseEntity.ok(orderResponseList);
    }

    @GetMapping("{id}")
    public ResponseEntity getEachOrder(@PathVariable long id){
        CreateOrderResponse createOrderResponse = orderService.getEachOrderById(id);
        return ResponseEntity.ok(createOrderResponse);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseAll>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size){
        List<OrderResponseAll> orderResponses = orderService.getAll(page - 1, size);
        return ResponseEntity.ok(orderResponses);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('SALE_STAFF')")
    public ResponseEntity update(@PathVariable long id, @RequestBody @Valid UpdateOrderRequest orderRequest) throws Exception {
        UpdateOrderResponse updateOrder = orderService.updateBySale(id,orderRequest);
        return ResponseEntity.ok(updateOrder);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity delete(@PathVariable long id) {
        DeleteOrderResponse deleteOrder = orderService.delete(id);
        return ResponseEntity.ok(deleteOrder);
    }
}