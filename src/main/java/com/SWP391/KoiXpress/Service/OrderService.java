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
import org.antlr.v4.runtime.atn.SemanticContext;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
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
        double totalVolume = 0;
        int totalBox = 0;
        int totalQuantityFish = 0;

        order.setUser(user);
        order.setOrderDate(new Date());
        order.setDeliveryDate(Date.from(LocalDate.now().plusWeeks(4).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        order.setOriginLocation(orderRequest.getOriginLocation());
        order.setDestinationLocation(orderRequest.getDestinationLocation());
        order.setPaymentMethod(orderRequest.getPaymentMethod());
        order.setCustomerNotes(orderRequest.getCustomerNotes());
        order.setOrderStatus(orderRequest.getOrderStatus());


        List<OrderDetail> orderDetails = new ArrayList<>();
        for(OrderDetailRequest orderDetailRequest : orderRequest.getOrderDetailRequestList()){
            Map<Double, Integer> fishSizeQuantityMap = Map.of(orderDetailRequest.getSizeOfFish(), orderDetailRequest.getNumberOfFish());
            BoxDetail boxDetail = boxDetailService.createBox(fishSizeQuantityMap);
            OrderDetail orderDetail= new OrderDetail();

            orderDetail.setOrder(order);
            orderDetail.setBoxDetail(boxDetail);
            orderDetail.setPriceOfFish(orderDetailRequest.getPriceOfFish());
            orderDetail.setNameFarm(orderDetailRequest.getNameFarm());
            orderDetail.setFarmAddress(orderDetailRequest.getFarmAddress());
            orderDetail.setOrigin(orderDetailRequest.getOrigin());
            orderDetail.setDestination(orderDetailRequest.getDestination());
            orderDetail.setRecipientInfo(orderDetailRequest.getRecipientInfo());
            orderDetail.setInspectionDate(Date.from(LocalDate.now().minusYears(1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
            orderDetail.setFishSpecies(orderDetailRequest.getFishSpecies());
            orderDetail.setNumberOfFish(orderDetailRequest.getNumberOfFish());
            orderDetail.setSizeOfFish(orderDetailRequest.getSizeOfFish());
            orderDetail.setHealthFishStatus(orderDetailRequest.getHealthFishStatus());
            orderDetail.setOrderStatus(orderDetailRequest.getOrderStatus());


            orderDetail.setPrice(boxDetail.getTotalPrice());
            orderDetail.setTotalBox(boxDetail.getTotalBox());
            orderDetail.setTotalVolume(boxDetail.getTotalVolume());
            orderDetails.add(orderDetail);

        }
        for (OrderDetail orderDetail : orderDetails) {
            totalPrice += orderDetail.getPrice(); // Cộng dồn giá
            totalBox += orderDetail.getTotalBox(); // Cộng dồn số lượng
            totalVolume += orderDetail.getTotalVolume();
            totalQuantityFish += orderDetail.getNumberOfFish();// Cộng dồn thể tích
        }
        order.setOrderDetails(orderDetails);
        order.setTotalPrice(totalPrice);
        order.setTotalBox(totalBox);
        order.setTotalVolume(totalVolume);
        order.setTotalQuantity(totalQuantityFish);
        orderRepository.save(order);
        return modelMapper.map(order, OrderResponse.class);
    }

    public List<OrderResponse> getAllOrdersByCurrentUser() {
        User user = authenticationService.getCurrentUser();
        List<Order> orders = orderRepository.findOrdersByUser(user);
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
        oldOrder.setOrderStatus(OrderStatus.Canceled);
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

    public List<OrderResponse> getAll(){
        List<Order> orders = orderRepository.findAll();
        List<OrderResponse> orderResponses = new ArrayList<>();
        for(Order order : orders){
            OrderResponse orderResponse = modelMapper.map(order, OrderResponse.class);
            orderResponses.add(orderResponse);
        }
        return orderResponses;
    }
    public List<Order> getAllOrders(){
        List<Order> orderList = orderRepository.findAll();
        return orderList;
    }
}
