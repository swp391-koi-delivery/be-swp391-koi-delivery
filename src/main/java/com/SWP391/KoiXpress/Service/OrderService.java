package com.SWP391.KoiXpress.Service;

import com.SWP391.KoiXpress.Entity.BoxDetail;
import com.SWP391.KoiXpress.Entity.Enum.DescribeOrder;
import com.SWP391.KoiXpress.Entity.Enum.OrderStatus;
import com.SWP391.KoiXpress.Entity.Order;
import com.SWP391.KoiXpress.Entity.OrderDetail;
import com.SWP391.KoiXpress.Entity.User;
import com.SWP391.KoiXpress.Exception.EntityNotFoundException;
import com.SWP391.KoiXpress.Exception.NotFoundException;
import com.SWP391.KoiXpress.Model.request.OrderDetailRequest;
import com.SWP391.KoiXpress.Model.request.OrderRequest;
import com.SWP391.KoiXpress.Model.response.BoxDetailResponse;
import com.SWP391.KoiXpress.Model.response.OrderResponse;
import com.SWP391.KoiXpress.Repository.OrderRepository;
import com.SWP391.KoiXpress.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
    public Order create(OrderRequest orderRequest) {
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
            BoxDetail boxDetail = calculateBoxService.createBox(fishSizeQuantityMap);
            OrderDetail orderDetail= new OrderDetail();

            orderDetail.setOrder(order);
            orderDetail.setBoxDetail(boxDetail);
            orderDetail.setType(orderDetail.getType());
            totalPrice = boxDetail.getLargeBox()*8.05 + boxDetail.getMediumBox() *4.02 + boxDetail.getSmallBox() * 2.01;
            orderDetail.setPrice(boxDetail.getPrice());
            totalBox =  boxDetail.getLargeBox()+boxDetail.getMediumBox()+boxDetail.getSmallBox();
            orderDetails.add(orderDetail);

        }
        order.setOrderDetails(orderDetails);
        order.setTotalquantity(totalBox);
        order.setPrice(totalPrice);
        return orderRepository.save(order);
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
