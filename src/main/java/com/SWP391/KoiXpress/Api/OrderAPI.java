package com.SWP391.KoiXpress.Api;


import com.SWP391.KoiXpress.Entity.Order;
import com.SWP391.KoiXpress.Model.request.OrderRequest;
import com.SWP391.KoiXpress.Model.response.OrderResponse;
import com.SWP391.KoiXpress.Service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin("*")
//@PreAuthorize("hasAuthority('SALESSTAFF')")
public class OrderAPI {

    @Autowired
    OrderService orderService;

    // Create order
    @PostMapping
    public ResponseEntity create(@RequestBody OrderRequest orderRequest){
        Order order = orderService.create(orderRequest);
        return ResponseEntity.ok(order);
    }

    // Get all orders
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<OrderResponse> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    // Update order
    @PutMapping("/{orderId}")
    public ResponseEntity<OrderResponse> updateOrder(@PathVariable long orderId,@Valid @RequestBody  OrderRequest orderRequest) {
        OrderResponse updatedOrder = orderService.update(orderId, orderRequest);
        return ResponseEntity.ok(updatedOrder);
    }

    // Delete order (soft delete)
    @DeleteMapping("/{orderId}")
    public ResponseEntity<OrderResponse> deleteOrder(@PathVariable long orderId) {
        OrderResponse deletedOrder = orderService.delete(orderId);
        return ResponseEntity.ok(deletedOrder);
    }


}
