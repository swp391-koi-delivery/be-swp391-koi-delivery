package com.SWP391.KoiXpress.Service;

import com.SWP391.KoiXpress.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;



//    private String generateTrackingOrder() {
//        String trackingOrder;
//        boolean exists;
//        do {
//            // Tạo 6 chữ số ngẫu nhiên
//            int randomNumber = (int) (Math.random() * 1000000);
//            trackingOrder = String.format("ORD%09d", randomNumber);
//
//            // Kiểm tra xem username đã tồn tại trong cơ sở dữ liệu chưa
//            exists = orderRepository.existsByTrackingOrder(trackingOrder);
//        } while (exists); // Lặp lại cho đến khi tìm được username duy nhất
//        return trackingOrder;
//    }
}
