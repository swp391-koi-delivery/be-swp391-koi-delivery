package com.SWP391.KoiXpress.Api;//package com.SWP391.KoiXpress.Api;
//
//import com.SWP391.KoiXpress.Entity.Order;
//import com.SWP391.KoiXpress.Service.KoiFishService;
//import com.SWP391.KoiXpress.Service.OrderService;
//import com.SWP391.KoiXpress.Service.ShippingService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/v1/shipping")
//@CrossOrigin("*")
//public class ShippingAPI {
//
//    @Autowired
//    private ShippingService shippingService;
//    @Autowired
//    KoiFishService koiFishService;
//
//    /**
//     * API endpoint để tính toán chi phí vận chuyển dựa trên orderId.
//     *
//     * @param orderId Id của đơn hàng
//     * @return Chuỗi thông tin về chi phí và các điểm dừng
//     */
//    @GetMapping("/{orderId}")
//    public String calculateShippingCosts(@PathVariable long orderId) {
//        try {
//            Optional<Order> optionalOrder = shippingService.getOrderById(orderId);
//            if (!optionalOrder.isPresent()) {
//                return "Order not found for ID: " + orderId;
//            }
//
//            Order order = optionalOrder.get();
//
//            // Tính trọng lượng cá Koi dựa trên orderId
//            double totalWeight = koiFishService.calculateTotalWeightByOrderId(order.getOrderId());
//
//            // Kiểm tra xem trọng lượng có hợp lệ không
//            if (totalWeight <= 0) {
//                return "Invalid weight for order ID: " + orderId;
//            }
//
//            // Logging để kiểm tra các trường của order có null hay không
//            System.out.println("Order ID: " + order.getOrderId());
//            System.out.println("Order Destination: " + order.getDestinationLocation());
//            System.out.println("Order Weight: " + totalWeight);
//
//            // Tạo danh sách chứa đơn hàng và tính toán chi phí vận chuyển
//            List<Order> orders = List.of(order);
//            return shippingService.planAndCalculateCosts(orders);
//        } catch (Exception e) {
//            // Trả về thông báo lỗi nếu có bất kỳ ngoại lệ nào xảy ra
//            return "Error calculating shipping costs: " + e.getMessage();
//
//        }
//
//
//    }
//    @GetMapping("/multiple-orders")
//    public String calculateShippingCostsForMultipleOrders(@RequestParam List<Long> orderIds) {
//        List<Order> orders = new ArrayList<>();
//
//        for (Long orderId : orderIds) {
//            Optional<Order> optionalOrder = shippingService.getOrderById(orderId);
//            if (!optionalOrder.isPresent()) {
//                return "Order not found for ID: " + orderId;
//            }
//            orders.add(optionalOrder.get());
//        }
//
//        return shippingService.planAndCalculateCosts(orders);
//    }
//
//}
