package com.SWP391.KoiXpress.Service;

import com.SWP391.KoiXpress.Entity.Order;
import com.SWP391.KoiXpress.Exception.EntityNotFoundException;
import com.SWP391.KoiXpress.Model.Request.OrderRequest;
import com.SWP391.KoiXpress.Model.Response.OrderResponse;
import com.SWP391.KoiXpress.Repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ModelMapper modelMapper;

    // Create
    public OrderResponse create(OrderRequest orderRequest) {
        // Use ModelMapper to map the request DTO to the entity
        Order order = modelMapper.map(orderRequest, Order.class);
        Order newOrder = orderRepository.save(order);
        // Map the saved entity to the response DTO
        return modelMapper.map(newOrder, OrderResponse.class);
    }

    // Read all orders
    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findOrdersByIsDeletedFalse();
        // Map the entity list to a list of response DTOs
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderResponse.class))
                .collect(Collectors.toList());
    }

    // Update
    public OrderResponse update(long orderId, OrderRequest orderRequest) {
        Order oldOrder = getOrderById(orderId);

        // Use ModelMapper to update the entity with the new data
        modelMapper.map(orderRequest, oldOrder);

        Order updatedOrder = orderRepository.save(oldOrder);
        // Return the updated order as a response DTO
        return modelMapper.map(updatedOrder, OrderResponse.class);
    }

    // Delete (soft delete)
    public OrderResponse delete(long orderId) {
        Order oldOrder = getOrderById(orderId);
        oldOrder.setDeleted(true);
        Order deletedOrder = orderRepository.save(oldOrder);
        return modelMapper.map(deletedOrder, OrderResponse.class);
    }

    // Helper method to get order by id
    private Order getOrderById(long orderId) {
        Order oldOrder = orderRepository.findOrderByOrderId(orderId);
        if (oldOrder == null) {
            throw new EntityNotFoundException("Order not found");
        }
        return oldOrder;
    }
}
