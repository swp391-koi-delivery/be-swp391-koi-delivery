package com.SWP391.KoiXpress.Service;

import com.SWP391.KoiXpress.Entity.Enum.OrderStatus;
import com.SWP391.KoiXpress.Entity.Order;
import com.SWP391.KoiXpress.Entity.Vehicle;
import com.SWP391.KoiXpress.Model.request.VehicleRequest;
import com.SWP391.KoiXpress.Model.response.OrderResponse;
import com.SWP391.KoiXpress.Repository.OrderRepository;
import com.SWP391.KoiXpress.Repository.VehicleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleService {
    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    OrderRepository orderRepository;

    public List<OrderResponse> createVehicle(List<Order> orders, VehicleRequest vehicleRequest) {
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleType(vehicleRequest.getVehicleType());
        double maxVolume = vehicle.getMaxVolume();
        List<Order> unplacedOrders = orders.stream()
                .filter(order -> order.getOrderStatus() == OrderStatus.Shipping)// Lọc theo trạng thái mong muốn
                .filter(order -> order.getVehicle() == null)
                .sorted(Comparator.comparing(Order::getOrderDate).reversed() )
                .collect(Collectors.toList());
        int n = unplacedOrders.size();
        int dp[][]= new int[n + 1][(int)maxVolume + 1];

        for (int i = 1; i<= n; i++){
            Order order = unplacedOrders.get(i-1);
            for(int j = 1; j <= maxVolume ; j++){
                dp[i][j] = dp[i-1][j];
                if(j>= order.getTotalVolume()) {
                    dp[i][j] = Math.max(dp[i][j], dp[i-1][j- (int)order.getTotalVolume()] + order.getTotalBox());
                }
            }
        }
        List<Order> currentVehicleOrders = new ArrayList<>();

        for (int i = n; i > 0 && maxVolume > 0; i--) {
            if (dp[i][(int)maxVolume] != dp[i - 1][(int)maxVolume]) {
                // Đơn hàng thứ i đã được chọn
                currentVehicleOrders.add(unplacedOrders.get(i - 1));
                maxVolume -= unplacedOrders.get(i - 1).getTotalVolume(); // Giảm thể tích còn lại
            }
        }
        vehicleRepository.save(vehicle);
        for(Order order: currentVehicleOrders){
            order.setVehicle(vehicle);
        }
        List<OrderResponse> orderResponses = currentVehicleOrders.stream()
                .map(order -> modelMapper.map(order, OrderResponse.class)) // Giả sử OrderResponse có các trường này
                .collect(Collectors.toList());
        orderRepository.saveAll(currentVehicleOrders);
        return orderResponses;
    }
}
