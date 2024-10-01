package com.SWP391.KoiXpress.Service;

import com.SWP391.KoiXpress.Entity.Order;

import com.SWP391.KoiXpress.Entity.Vehicle;
import com.SWP391.KoiXpress.Repository.OrderRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShippingService {


    @Autowired
    private RoutingService routingService;

    @Autowired
    private GeoCodingService geoCodingService;

    @Autowired
    KoiFishService koiFishService;

    @Autowired
    OrderRepository orderRepository;

    /**
     * Lập kế hoạch vận chuyển cho danh sách các đơn hàng.
     *
     * @param orders danh sách đơn hàng
     * @return thông tin vận chuyển
     */

//    public String planAndCalculateCosts(List<Order> orders) {
//        if (orders == null || orders.isEmpty()) {
//            return "No orders provided.";
//        }
//
//        Truck truck = new Truck();
//        truck.setTotalCapacity(10000); // Full capacity at the start in HCM
//        double totalRouteDistance = 0;
//        double totalCost = 0;
//
//        truck.setRemainingCapacity(0); // Fully loaded means no remaining capacity initially
//        truck.setCurrentLocation("Hồ Chí Minh"); // Đây là giá trị mặc định
//
//        StringBuilder resultDetails = new StringBuilder("Route details:\n");
//
//        for (Order order : orders) {
//            double totalWeight = koiFishService.calculateTotalWeightByOrderId(order.getOrderId());
//
//            if (truck.getTotalCapacity() - truck.getRemainingCapacity() >= totalWeight) {
//                truck.addStop(new RouteStop(order.getDestinationLocation(), totalWeight));
//                truck.setRemainingCapacity(truck.getRemainingCapacity() + totalWeight);
//
//                String startLocation = "Hồ Chí Minh";  // Luôn là Hồ Chí Minh
//                String destinationLocation = order.getDestinationLocation();  // Đích đến từ order
//
//                try {
//                    // Gọi RoutingService để lấy thông tin lộ trình
//                    String routeSegment = routingService.getRouteSegment(startLocation, destinationLocation);
//                    System.out.println("Route Segment JSON: " + routeSegment); // Kiểm tra JSON trả về
//
//                    // Phân tích JSON để lấy khoảng cách
//                    double distanceToNextStop = parseDistanceFromRouteSegment(routeSegment);
//
//                    totalRouteDistance += distanceToNextStop;
//                    truck.setCurrentLocation(order.getDestinationLocation());
//
//                    // Tính toán chi phí vận chuyển
//                    double costForSegment = calculateSegmentCost(totalWeight);
//                    totalCost += costForSegment;
//
//                    resultDetails.append(String.format(
//                            "Stop at: %s, Distance: %.0f km, Weight delivered: %.1f kg, Cost: $%.2f\n",
//                            order.getDestinationLocation(),
//                            distanceToNextStop,
//                            totalWeight,
//                            costForSegment
//                    ));
//
//                } catch (Exception e) {
//                    return "Error processing order for " + destinationLocation + ": " + e.getMessage();
//                }
//            } else {
//                return "Not enough capacity for order to " + order.getDestinationLocation();
//            }
//        }
//
//        resultDetails.append(String.format("Total distance: %.0f km, Total cost: $%.2f", totalRouteDistance, totalCost));
//        return resultDetails.toString();
//    }

    // Tính toán chi phí vận chuyển dựa trên trọng lượng
    public String planAndCalculateCosts(List<Order> orders) {
        if (orders == null || orders.isEmpty()) {
            return "No orders provided.";
        }

        Vehicle vehicle = new Vehicle();
        vehicle.setTotalCapacity(1000); // Full capacity at the start in HCMC
        double totalRouteDistance = 0;
        double totalCost = 0;
        vehicle.setRemainingCapacity(0);
        vehicle.setCurrentLocation("Hồ Chí Minh");

        StringBuilder resultDetails = new StringBuilder("Route details:\n");

        // Tạo danh sách các điểm dừng chưa xử lý
        List<Order> remainingOrders = new ArrayList<>(orders);

        while (!remainingOrders.isEmpty()) {
            // Tìm điểm đến gần nhất
            Order nextOrder = null;
            double minDistance = Double.MAX_VALUE;

            for (Order order : remainingOrders) {
                double distance = calculateDistanceBetween(vehicle.getCurrentLocation(), order.getDestinationLocation());
                if (distance < minDistance) {
                    minDistance = distance;
                    nextOrder = order;
                }
            }

            if (nextOrder != null) {
                double totalWeight = koiFishService.calculateTotalWeightByOrderId(nextOrder.getOrderId());

                if (vehicle.getTotalCapacity() - vehicle.getRemainingCapacity() >= totalWeight) {
                    // Giao hàng tại điểm đến
                    vehicle.addStop(new RouteStop(nextOrder.getDestinationLocation(), totalWeight));
                    vehicle.setRemainingCapacity(vehicle.getRemainingCapacity() + totalWeight);

                    // Cập nhật khoảng cách và vị trí hiện tại
                    totalRouteDistance += minDistance;
                    vehicle.setCurrentLocation(nextOrder.getDestinationLocation());

                    // Tính toán chi phí cho đoạn đường hiện tại
                    double costForSegment = calculateSegmentCost(totalWeight);
                    totalCost += costForSegment;


                    // Cập nhật chi tiết về điểm dừng
                    resultDetails.append("Stop at: ").append(nextOrder.getDestinationLocation())
                            .append(", Order ID: ").append(nextOrder.getOrderId())  // Thêm thông tin Order ID
                            .append(", Distance: ").append(String.format("%.0f", minDistance)).append(" km")  // Khoảng cách được làm tròn
                            .append(", Weight delivered: ").append(totalWeight).append(" kg")  // Trọng lượng hàng hóa
                            .append(", Cost: $").append(String.format("%.2f", costForSegment))  // Chi phí
                            .append("\nOrder #").append(nextOrder.getOrderId()).append(" delivery successful.\n");  // Thông báo giao hàng thành công

                    // Xóa đơn hàng khỏi danh sách các đơn chưa xử lý
                    remainingOrders.remove(nextOrder);
                } else {
                    return "Not enough capacity for order to " + nextOrder.getDestinationLocation();
                }
            }
        }

        resultDetails.append("Total distance: ").append(String.format("%.0f", totalRouteDistance)).append(" km, Total cost: $").append(String.format("%.2f", totalCost));
        return resultDetails.toString();
    }

    private double calculateSegmentCost(double totalWeight) {
        if (totalWeight <= 10) {
            return 1.5;
        } else {
            double extraWeight = totalWeight - 10;
            if (extraWeight <= 50) {
                return 1.5 + (extraWeight * 0.5);
            } else {
                double additionalWeight = extraWeight - 50;
                return 1.5 + (50 * 0.5) + (additionalWeight * 0.1);
            }
        }
    }

    // Phân tích JSON để lấy khoảng cách từ phản hồi lộ trình
    public double parseDistanceFromRouteSegment(String routeSegment) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(routeSegment);
            JsonNode pathsNode = rootNode.path("paths");

            if (pathsNode.isArray() && pathsNode.size() > 0) {
                double distanceInMeters = pathsNode.get(0).path("distance").asDouble();
                return distanceInMeters / 1000.0;  // Chuyển đổi từ mét sang km
            } else {
                System.out.println("No distance information found in the route segment");
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Phương thức getOrderById để lấy thông tin Order từ database dựa trên orderId
    public Optional<Order> getOrderById(long orderId) {
        // Sử dụng Optional để xử lý việc tìm Order từ database
        return orderRepository.findById(orderId);
    }

    public double calculateDistanceBetween(String location1, String location2) {
        try {
            double[] coords1 = geoCodingService.geocoding(location1);
            double[] coords2 = geoCodingService.geocoding(location2);
            return routingService.calculateDistance(coords1, coords2);
        } catch (Exception e) {
            e.printStackTrace(); // Ghi log lỗi hoặc xử lý lỗi tùy vào yêu cầu
            return 0; // Trả về giá trị mặc định nếu xảy ra lỗi
        }
    }


}




