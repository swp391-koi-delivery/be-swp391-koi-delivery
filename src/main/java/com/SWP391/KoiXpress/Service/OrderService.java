package com.SWP391.KoiXpress.Service;


import com.SWP391.KoiXpress.Entity.BoxDetail;
import com.SWP391.KoiXpress.Entity.Enum.OrderStatus;
import com.SWP391.KoiXpress.Entity.Order;
import com.SWP391.KoiXpress.Entity.OrderDetail;
import com.SWP391.KoiXpress.Entity.User;
import com.SWP391.KoiXpress.Exception.EntityNotFoundException;
import com.SWP391.KoiXpress.Exception.NotFoundException;
import com.SWP391.KoiXpress.Model.request.OrderDetailRequest;
import com.SWP391.KoiXpress.Model.request.OrderRequest;
import com.SWP391.KoiXpress.Model.response.OrderResponse;
import com.SWP391.KoiXpress.Repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    BoxDetailService boxDetailService;

    // Create
    public OrderResponse create(OrderRequest orderRequest) {
        User user = authenticationService.getCurrentUser();
        Order order = new Order();
        double totalPrice = 0;
        int totalBox = 0;
        order.setUser(user);
        order.setOrderDate(new Date());

        order.setOriginLocation(orderRequest.getOriginLocation());
        order.setDestinationLocation(orderRequest.getDestinationLocation());
        order.setPayment(orderRequest.getPayment());
        order.setDescribeOrder(orderRequest.getDescribeOrder());
        order.setOrderStatus(orderRequest.getOrderStatus());
        order.setPaymentStatus(orderRequest.getPaymentStatus());

        List<OrderDetail> orderDetails = new ArrayList<>();
        for(OrderDetailRequest orderDetailRequest : orderRequest.getOrderDetailRequestList()){
            Map<Double, Integer> fishSizeQuantityMap = Map.of(orderDetailRequest.getSize(), orderDetailRequest.getQuantity());
            BoxDetail boxDetail = boxDetailService.createBox(fishSizeQuantityMap);
            OrderDetail orderDetail= new OrderDetail();

            orderDetail.setOrder(order);
            orderDetail.setBoxDetail(boxDetail);
            orderDetail.setType(orderDetailRequest.getType());
            orderDetail.setQuantity(orderDetailRequest.getQuantity());
            orderDetail.setSize(orderDetailRequest.getSize());
            totalPrice = boxDetail.getLargeBox()*8.05 + boxDetail.getMediumBox() *4.02 + boxDetail.getSmallBox() * 2.01;
            orderDetail.setPrice(boxDetail.getTotalPrice());
            totalBox =  boxDetail.getLargeBox()+boxDetail.getMediumBox()+boxDetail.getSmallBox();
            orderDetails.add(orderDetail);

        }
        order.setOrderDetails(orderDetails);
        order.setTotalquantity(totalBox);
        order.setPrice(totalPrice);
        orderRepository.save(order);
        return modelMapper.map(order, OrderResponse.class);
    }

    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderResponse.class))
                .collect(Collectors.toList());
    }

    // Update
    public OrderResponse update(long id, OrderRequest orderRequest) {
        Order oldOrder = getOrderById(id);
        modelMapper.map(orderRequest, oldOrder);

        Order updatedOrder = orderRepository.save(oldOrder);
        return modelMapper.map(updatedOrder, OrderResponse.class);
    }

    public OrderResponse delete(long id) {
        Order oldOrder = getOrderById(id);
        oldOrder.setOrderStatus(OrderStatus.OrderCancel);
        Order deletedOrder = orderRepository.save(oldOrder);
        return modelMapper.map(deletedOrder, OrderResponse.class);
    }

    private Order getOrderById(long id) {
        Order oldOrder = orderRepository.findOrderById(id);
        if (oldOrder == null) {
            throw new EntityNotFoundException("Order not found");
        }
        return oldOrder;
    }

    public OrderResponse getEachOrderById(long id){
        Order order = orderRepository.findOrderById(id);
        if(order==null){
            throw new NotFoundException("Order not found");
        }
        return modelMapper.map(order, OrderResponse.class);
    }
}
