package com.SWP391.KoiXpress.Service;

import com.SWP391.KoiXpress.Entity.BoxDetail;
import com.SWP391.KoiXpress.Entity.Enum.DescribeOrder;
import com.SWP391.KoiXpress.Entity.Enum.OrderStatus;
import com.SWP391.KoiXpress.Entity.Order;
import com.SWP391.KoiXpress.Entity.User;
import com.SWP391.KoiXpress.Exception.EntityNotFoundException;
import com.SWP391.KoiXpress.Exception.NotFoundException;
import com.SWP391.KoiXpress.Model.request.OrderRequest;
import com.SWP391.KoiXpress.Model.response.BoxDetailResponse;
import com.SWP391.KoiXpress.Model.response.OrderResponse;
import com.SWP391.KoiXpress.Repository.OrderRepository;
import com.SWP391.KoiXpress.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CalculateBoxService calculateBoxService;

    // Create
    public OrderResponse create(OrderRequest orderRequest) {
        try {
            Map<Double, Integer> fishSizeQuantityMap = Map.of(orderRequest.getSize(), orderRequest.getQuantity());
            BoxDetail boxDetail = calculateBoxService.createBox(fishSizeQuantityMap);
            Order order = modelMapper.map(orderRequest, Order.class);
            order.setBoxDetail(boxDetail);
            Order newOrder = orderRepository.save(order);
            OrderResponse orderResponse = modelMapper.map(newOrder, OrderResponse.class);
            orderResponse.setTotalPrice(boxDetail.getTotalPrice());
            orderResponse.setBoxDetail(modelMapper.map(newOrder.getBoxDetail(), BoxDetailResponse.class));
            return orderResponse;

        } catch (Exception e) {
            // Log chi tiết lỗi
            e.printStackTrace();
            throw new RuntimeException("Đã xảy ra lỗi trong quá trình tạo Order", e);
        }
    }


    // Read all orders
    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderResponse.class))
                .collect(Collectors.toList());
    }

    // Update
    public OrderResponse update(long orderId, OrderRequest orderRequest) {
        Order oldOrder = getOrderById(orderId);
        modelMapper.map(orderRequest, oldOrder);
        Order updatedOrder = orderRepository.save(oldOrder);
        return modelMapper.map(updatedOrder, OrderResponse.class);
    }


    // Delete
    public OrderResponse delete(long orderId) {
        Order oldOrder = getOrderById(orderId);
        oldOrder.setOrderStatus(OrderStatus.OrderCanceled);
        Order deletedOrder = orderRepository.save(oldOrder);
        return modelMapper.map(deletedOrder, OrderResponse.class);
    }


    private Order getOrderById(long orderId) {
        Order oldOrder = orderRepository.findOrderByOrderId(orderId);
        if (oldOrder == null) {
            throw new EntityNotFoundException("Order not found");
        }
        return oldOrder;
    }

}
