package com.SWP391.KoiXpress.Service;

import com.SWP391.KoiXpress.Entity.*;
import com.SWP391.KoiXpress.Entity.Enum.OrderStatus;
import com.SWP391.KoiXpress.Exception.NotFoundException;
import com.SWP391.KoiXpress.Model.request.ProgressRequest;
import com.SWP391.KoiXpress.Model.response.OrderResponse;
import com.SWP391.KoiXpress.Model.response.OrderResponseAll;
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

    public List<OrderResponseAll> create(ProgressRequest progressRequest){
        Progress progress = new Progress();
        User delivery = authenticationService.getCurrentUser();
        progress.setUser(delivery);
        progress.setDateProgress(new Date());
        progress.setInProgress(true);
        double totalVolume = 0;
        int totalBox = 0;

        WareHouse wareHouse = wareHouseRepository.findWareHouseById(progressRequest.getWareHouseId());
        List<Order> orderList = orderRepository.findOrdersByOriginLocationContainingIgnoreCase(wareHouse.getLocation());
        List<Order> unplacedOrders = orderList.stream()
                .filter(order -> order.getOrderStatus() == OrderStatus.Shipping)
                .filter(order -> order.getProgress() == null)
                .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                .toList();

        if (unplacedOrders.isEmpty()) {
            throw new NotFoundException("Empty List");
        }

        // Thực hiện gom các đơn hàng vào Progress
        for (Order order : unplacedOrders) {
            order.setProgress(progress);
            totalVolume += order.getTotalVolume();
            totalBox += order.getTotalBox();
        }
        progress.setTotalVolume(totalVolume);
        progress.setTotalBox(totalBox);
        // Lưu Progress và các đơn hàng đã cập nhật
        progressRepository.save(progress);
        orderRepository.saveAll(unplacedOrders); // Lưu tất cả các đơn hàng đã cập nhật


        List<OrderResponseAll> response = unplacedOrders.stream()
                .map(order -> modelMapper.map(order, OrderResponseAll.class))
                .collect(Collectors.toList());

        return response;
    }
}
//        Map<String,List<Order>> ordersByWareHouse = unplacedOrders.stream().collect(Collectors.groupingBy(Order::getDestinationLocation));