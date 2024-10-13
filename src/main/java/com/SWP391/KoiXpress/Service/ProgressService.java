package com.SWP391.KoiXpress.Service;

import com.SWP391.KoiXpress.Entity.*;
import com.SWP391.KoiXpress.Entity.Enum.OrderStatus;
import com.SWP391.KoiXpress.Model.request.ProgressRequest;
import com.SWP391.KoiXpress.Repository.OrderRepository;
import com.SWP391.KoiXpress.Repository.ProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProgressService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProgressRepository progressRepository;

    @Autowired
    AuthenticationService authenticationService;

    public List<Order> create(List<Order> orders, ProgressRequest progressRequest){
        Progress progress = new Progress();
        User delivery = authenticationService.getCurrentUser();
        progress.setNameDelivery(delivery.getFullname());
        progress.setPhone(delivery.getPhone());
        Vehicle vehicle = new Vehicle();
        double maxVolume = vehicle.getMaxVolume();
        List<Order> unplacedOrders = orders.stream()
                .filter(order -> order.getOrderStatus() == OrderStatus.Shipping)
                .filter(order -> order.getProgress() == null)
                .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                .toList();
        return null;
    }
}
