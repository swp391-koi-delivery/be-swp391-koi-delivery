package com.SWP391.KoiXpress.Service;//package com.SWP391.KoiXpress.Service;
//
//import com.SWP391.KoiXpress.Entity.Enum.FishHealthStatus;
//import com.SWP391.KoiXpress.Entity.KoiFish;
//import com.SWP391.KoiXpress.Repository.KoiFishRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Service
//public class KoiFishService {
//
//    @Autowired
//    KoiFishRepository koiFishRepository;
//
//    // Lấy số lượng cá theo từng trạng thái sức khỏe cho một đơn hàng
//    public Map<FishHealthStatus, Long> countFishByHealthStatus(long orderId) {
//        List<KoiFish> koiFishList = koiFishRepository.findByOrderOrderId(orderId);
//
//        // Sử dụng Stream API để đếm số lượng cá theo từng trạng thái sức khỏe
//        Map<FishHealthStatus, Long> fishCountByHealthStatus = koiFishList.stream()
//                .collect(Collectors.groupingBy(KoiFish::getHealthFishStatus, Collectors.counting()));
//
//        return fishCountByHealthStatus;
//    }
//    public double calculateTotalWeightByOrderId(Long orderId) {
//        List<KoiFish> koiFishes = koiFishRepository.findByOrderOrderId(orderId);
//
//        // Log the fish data for debugging
//        koiFishes.forEach(koiFish -> {
//            System.out.println("KoiFish ID: " + koiFish.getKoiFishId() +
//                    ", Weight: " + koiFish.getWeight() +
//                    ", Quantity: " + koiFish.getQuantity());
//        });
//
//        return koiFishes.stream()
//                .mapToDouble(koiFish -> koiFish.getWeight() * koiFish.getQuantity())  // Weight * Quantity
//                .sum();
//    }
//
//}
