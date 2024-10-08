package com.SWP391.KoiXpress.Service;

//import com.SWP391.KoiXpress.Entity.Order;
//
//import com.SWP391.KoiXpress.Entity.Vehicle;
//import com.SWP391.KoiXpress.Repository.OrderRepository;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class ShippingService {
//
//
//    @Autowired
//    private RoutingService routingService;
//
//    @Autowired
//    private GeoCodingService geoCodingService;
//
//    @Autowired
//    OrderRepository orderRepository;
//
//    /**
//     * Lập kế hoạch vận chuyển cho danh sách các đơn hàng.
//     *
//     * @param orders danh sách đơn hàng
//     * @return thông tin vận chuyển
//     */
//
//
//    public String planAndCalculateCosts(List<Order> orders) {
//        if (orders == null || orders.isEmpty()) {
//            return "No orders provided.";
//        }
//
//        Vehicle vehicle = new Vehicle();
//        vehicle.setTotalCapacity(1000);  // Full capacity at the start
//        vehicle.setRemainingCapacity(vehicle.getTotalCapacity());  // Set remaining capacity to full capacity (1000)
//        double totalRouteDistance = 0;
//        double totalCost = 0;
//        vehicle.setCurrentLocation("Hồ Chí Minh");
//
//        StringBuilder resultDetails = new StringBuilder("Route details:\n");
//
//        List<Order> remainingOrders = new ArrayList<>(orders);
//
//        while (!remainingOrders.isEmpty()) {
//            Order nextOrder = null;
//            double minDistance = Double.MAX_VALUE;
//
//            for (Order order : remainingOrders) {
//                double distance = calculateDistanceBetween(vehicle.getCurrentLocation(), order.getDestinationLocation());
//                if (distance < minDistance) {
//                    minDistance = distance;
//                    nextOrder = order;
//                }
//            }
//
//            if (nextOrder != null) {
//                // Log the weight calculation process
//                System.out.println("Calculating total weight for Order ID: " + nextOrder.getOrderId());
//                double totalWeight = koiFishService.calculateTotalWeightByOrderId(nextOrder.getOrderId());
//                System.out.println("Total weight for Order ID " + nextOrder.getOrderId() + ": " + totalWeight);
//
//                // Log the current remaining capacity
//                System.out.println("Remaining capacity before order: " + vehicle.getRemainingCapacity());
//
//                // Check for invalid or excessive weight
//                if (totalWeight <= 0) {
//                    System.out.println("Order ID " + nextOrder.getOrderId() + " has invalid weight: " + totalWeight);
//                    return "Invalid weight for order ID " + nextOrder.getOrderId();
//                }
//
//                if (totalWeight > vehicle.getTotalCapacity()) {
//                    System.out.println("Order ID " + nextOrder.getOrderId() + " exceeds the vehicle's total capacity!");
//                    return "Order ID " + nextOrder.getOrderId() + " exceeds the vehicle's total capacity.";
//                }
//
//                if (vehicle.getRemainingCapacity() >= totalWeight) {
//                    vehicle.addStop(new RouteStop(nextOrder.getDestinationLocation(), totalWeight));
//                    vehicle.setRemainingCapacity(vehicle.getRemainingCapacity() - totalWeight);  // Subtract the delivered weight
//
//                    // Log remaining capacity after adding stop
//                    System.out.println("Remaining capacity after adding stop: " + vehicle.getRemainingCapacity());
//
//                    totalRouteDistance += minDistance;
//                    vehicle.setCurrentLocation(nextOrder.getDestinationLocation());
//
//                    double costForSegment = calculateSegmentCost(totalWeight);
//                    totalCost += costForSegment;
//
//                    resultDetails.append("Stop at: ").append(nextOrder.getDestinationLocation())
//                            .append(", Order ID: ").append(nextOrder.getOrderId())
//                            .append(", Distance: ").append(String.format("%.0f", minDistance)).append(" km")
//                            .append(", Weight delivered: ").append(totalWeight).append(" kg")
//                            .append(", Cost: $").append(String.format("%.2f", costForSegment))
//                            .append("\nOrder #").append(nextOrder.getOrderId()).append(" delivery successful.\n");
//
//                    remainingOrders.remove(nextOrder);
//                } else {
//                    System.out.println("Not enough capacity for order to " + nextOrder.getDestinationLocation());
//                    return "Not enough capacity for order to " + nextOrder.getDestinationLocation();
//                }
//            }
//        }
//
//        resultDetails.append("Total distance: ").append(String.format("%.0f", totalRouteDistance)).append(" km, Total cost: $").append(String.format("%.2f", totalCost));
//        return resultDetails.toString();
//    }
//
//
//
//    private double calculateSegmentCost(double totalWeight) {
//        if (totalWeight <= 10) {
//            return 1.5;
//        } else {
//            double extraWeight = totalWeight - 10;
//            if (extraWeight <= 50) {
//                return 1.5 + (extraWeight * 0.5);
//            } else {
//                double additionalWeight = extraWeight - 50;
//                return 1.5 + (50 * 0.5) + (additionalWeight * 0.1);
//            }
//        }
//    }
//
//    // Phân tích JSON để lấy khoảng cách từ phản hồi lộ trình
//    public double parseDistanceFromRouteSegment(String routeSegment) {
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            JsonNode rootNode = mapper.readTree(routeSegment);
//            JsonNode pathsNode = rootNode.path("paths");
//
//            if (pathsNode.isArray() && pathsNode.size() > 0) {
//                double distanceInMeters = pathsNode.get(0).path("distance").asDouble();
//                return distanceInMeters / 1000.0;  // Chuyển đổi từ mét sang km
//            } else {
//                System.out.println("No distance information found in the route segment");
//                return 0;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return 0;
//        }
//    }
//
//    // Phương thức getOrderById để lấy thông tin Order từ database dựa trên orderId
//    public Optional<Order> getOrderById(long orderId) {
//        // Sử dụng Optional để xử lý việc tìm Order từ database
//        return orderRepository.findById(orderId);
//    }
//
//    public double calculateDistanceBetween(String location1, String location2) {
//        try {
//            double[] coords1 = geoCodingService.geocoding(location1);
//            double[] coords2 = geoCodingService.geocoding(location2);
//            return routingService.calculateDistance(coords1, coords2);
//        } catch (Exception e) {
//            e.printStackTrace(); // Ghi log lỗi hoặc xử lý lỗi tùy vào yêu cầu
//            return 0; // Trả về giá trị mặc định nếu xảy ra lỗi
//        }
//    }
//
//
//}
//
//
//
//
