package com.SWP391.KoiXpress.Api;

import com.SWP391.KoiXpress.Entity.Order;
import com.SWP391.KoiXpress.Model.request.OrderRequest;
import com.SWP391.KoiXpress.Model.response.OrderResponse;
import com.SWP391.KoiXpress.Model.response.OrderResponseAll;
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
@CrossOrigin("*")
public class OrderAPI {

    @Autowired
    OrderService orderService;


    @PostMapping
    public ResponseEntity create(@Valid @RequestBody OrderRequest orderRequest){
        OrderResponse order = orderService.create(orderRequest);
        return ResponseEntity.ok(order);
    }
    @GetMapping("/each-user")
    public ResponseEntity get(){
        List<OrderResponse> orderResponseList = orderService.getAllOrdersByCurrentUser();
        return ResponseEntity.ok(orderResponseList);
    }

    @GetMapping("{id}")
    public ResponseEntity getEachOrder(long id){
        OrderResponse orderResponse = orderService.getEachOrderById(id);
        return ResponseEntity.ok(orderResponse);
    }
    @GetMapping
    public ResponseEntity getAll(){
        List<OrderResponseAll> orderResponses = orderService.getAll();
        return ResponseEntity.ok(orderResponses);
    }
    @PostMapping("{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody @Valid OrderRequest orderRequest){
        OrderResponse updateOrder = orderService.update(id,orderRequest);
        return ResponseEntity.ok(updateOrder);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable long id) {
        OrderResponse deleteOrder = orderService.delete(id);
        return ResponseEntity.ok(deleteOrder);
    }
}
