package com.SWP391.KoiXpress.Service;

import com.SWP391.KoiXpress.Entity.Enum.OrderStatus;
import com.SWP391.KoiXpress.Entity.Order;
import com.SWP391.KoiXpress.Entity.Progress;
import com.SWP391.KoiXpress.Entity.User;
import com.SWP391.KoiXpress.Entity.Vehicle;
import com.SWP391.KoiXpress.Model.request.VehicleRequest;
import com.SWP391.KoiXpress.Model.response.OrderResponse;
import com.SWP391.KoiXpress.Repository.OrderRepository;
import com.SWP391.KoiXpress.Repository.ProgressRepository;
import com.SWP391.KoiXpress.Repository.UserRepository;
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

    @Autowired
    ProgressRepository progressRepository;

    @Autowired
    UserRepository userRepository;

//    public List<Progress> createVehicle(VehicleRequest vehicleRequest) {
//        Vehicle vehicle = new Vehicle();
//        vehicle.setVehicleType(vehicleRequest.getVehicleType());
//        double maxVolume = vehicle.getMaxVolume();
//        List<Progress> progresses = progressRepository.findAll();
//        List<Progress> unplacedProgresses = progresses.stream()
//                .filter(progress -> !progress.isInProgress())// Lọc theo trạng thái mong muốn
//                .sorted(Comparator.comparing(Progress::getDateProgress).reversed() )
//                .toList();
//        int n = unplacedProgresses.size();
//        int[][] dp = new int[n + 1][(int)maxVolume + 1];
//
//        for (int i = 1; i<= n; i++){
//            Progress progress = unplacedProgresses.get(i-1);
//            for(int j = 1; j <= maxVolume ; j++){
//                dp[i][j] = dp[i-1][j];
//                if(j>= progress.getTotalVolume()) {
//                    dp[i][j] = Math.max(dp[i][j], dp[i-1][j- (int)progress.getTotalVolume()] + progress.getTotalBox());
//                }
//            }
//        }
//        List<Progress> currentVehicle_Progresses = new ArrayList<>();
//
//        for (int i = n; i > 0 && maxVolume > 0; i--) {
//            if (dp[i][(int)maxVolume] != dp[i - 1][(int)maxVolume]) {
//                // Đơn hàng thứ i đã được chọn
//                currentVehicle_Progresses.add(unplacedProgresses.get(i - 1));
//                maxVolume -= unplacedProgresses.get(i - 1).getTotalVolume(); // Giảm thể tích còn lại
//            }
//        }
//        vehicleRepository.save(vehicle);
//        for(Progress progress: currentVehicle_Progresses){
//            progress.setVehicle(vehicle);
//        }
//
//        progressRepository.saveAll(currentVehicle_Progresses);
//        return currentVehicle_Progresses;
//    }

        public Vehicle create (VehicleRequest vehicleRequest){
            User delivery = userRepository.findUserById(vehicleRequest.getIdDriver());
            Vehicle vehicle = new Vehicle();
            vehicle.setVehicleType(vehicleRequest.getVehicleType());
            vehicle.setDriverName(delivery.getFullname());
            vehicle.setDriverPhone(delivery.getPhone());
            vehicle.setVolume(vehicleRequest.getVehicleType().getVolume());
            return vehicleRepository.save(vehicle);
        }
}
//        List<OrderResponse> orderResponses = currentVehicleOrders.stream()
//                .map(order -> modelMapper.map(order, OrderResponse.class)) // Giả sử OrderResponse có các trường này
//                .collect(Collectors.toList());