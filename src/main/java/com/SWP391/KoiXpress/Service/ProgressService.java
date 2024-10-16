package com.SWP391.KoiXpress.Service;

import com.SWP391.KoiXpress.Entity.*;
import com.SWP391.KoiXpress.Entity.Enum.OrderStatus;
import com.SWP391.KoiXpress.Exception.NotFoundException;
import com.SWP391.KoiXpress.Exception.ProgressException;
import com.SWP391.KoiXpress.Model.request.ProgressRequest;
import com.SWP391.KoiXpress.Model.response.OrderResponse;
import com.SWP391.KoiXpress.Model.response.OrderResponseAll;
import com.SWP391.KoiXpress.Model.response.ProgressResponse;
import com.SWP391.KoiXpress.Model.response.UserResponse;
import com.SWP391.KoiXpress.Repository.OrderRepository;
import com.SWP391.KoiXpress.Repository.ProgressRepository;
import com.SWP391.KoiXpress.Repository.WareHouseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProgressService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProgressRepository progressRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    WareHouseRepository wareHouseRepository;

    @Autowired
    ModelMapper modelMapper;

//    public List<OrderResponseAll> create(ProgressRequest progressRequest){
//        Progress progress = new Progress();
//        User delivery = authenticationService.getCurrentUser();
//        progress.setUser(delivery);
//        progress.setDateProgress(new Date());
//        progress.setInProgress(true);
//        double totalVolume = 0;
//        int totalBox = 0;
//
//        WareHouse wareHouse = wareHouseRepository.findWareHouseById(progressRequest.getWareHouseId());
//        List<Order> orderList = orderRepository.findOrdersByOriginLocationContainingIgnoreCase(wareHouse.getLocation());
//        List<Order> unplacedOrders = orderList.stream()
//                .filter(order -> order.getOrderStatus() == OrderStatus.Shipping)
////                .filter(order -> order.getProgress() == null)
//                .sorted(Comparator.comparing(Order::getOrderDate).reversed())
//                .toList();
//
//        if (unplacedOrders.isEmpty()) {
//            throw new NotFoundException("Empty List");
//        }
//
//        // Thực hiện gom các đơn hàng vào Progress
//        for (Order order : unplacedOrders) {
////            order.setProgress(progress);
//            totalVolume += order.getTotalVolume();
//            totalBox += order.getTotalBox();
//        }
//        int curentStock = wareHouse.getCurrentStock();
//        wareHouse.setCurrentStock(curentStock + totalBox);
//        progress.setTotalVolume(totalVolume);
//        progress.setTotalBox(totalBox);
//        // Lưu Progress và các đơn hàng đã cập nhật
//        progressRepository.save(progress);
//        wareHouseRepository.save(wareHouse);
//        orderRepository.saveAll(unplacedOrders); // Lưu tất cả các đơn hàng đã cập nhật
//
//
//        List<OrderResponseAll> response = unplacedOrders.stream()
//                .map(order -> modelMapper.map(order, OrderResponseAll.class))
//                .collect(Collectors.toList());
//
//        return response;
//    }
//        Map<String,List<Order>> ordersByWareHouse = unplacedOrders.stream().collect(Collectors.groupingBy(Order::getDestinationLocation));

    public List<ProgressResponse> create (ProgressRequest progressRequest){
        Order order = orderRepository.findOrderById(progressRequest.getOrderId());
        List<Progress> existProgressOrder = progressRepository.findProgressesByOrderId(progressRequest.getOrderId());
        if(!existProgressOrder.isEmpty()){
            throw new ProgressException("Order already in another Progress");
        }
        if(order == null){
            throw new NotFoundException("Can not found order");
        }
        if(order.getOrderStatus() == OrderStatus.Shipping) {
            List<ProgressResponse> progresses = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                Progress progress = new Progress();
                progress.setOrder(order);
                progressRepository.save(progress);
                ProgressResponse response = new ProgressResponse();
                response.setId(progress.getId());
                progresses.add(response);
            }
            return progresses;
        }
        throw new ProgressException("Order is not ready to ship");
    }

    public List<ProgressResponse> trackingOrder(UUID trackingOrder){
        List<Progress> progresses = progressRepository.findProgressesByOrderIdAndStatusNotNull(trackingOrder);
        if(progresses!=null) {
            return progresses.stream().map(progress -> modelMapper.map(progress, ProgressResponse.class)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

        List<OrderResponseAll> response = unplacedOrders.stream()
                .map(order -> modelMapper.map(order, OrderResponseAll.class))
                .collect(Collectors.toList());

    }