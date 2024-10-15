package com.SWP391.KoiXpress.Service;


import com.SWP391.KoiXpress.Entity.*;
import com.SWP391.KoiXpress.Entity.Enum.HealthFishStatus;
import com.SWP391.KoiXpress.Entity.Enum.OrderStatus;
import com.SWP391.KoiXpress.Exception.EntityNotFoundException;
import com.SWP391.KoiXpress.Exception.NotFoundException;
import com.SWP391.KoiXpress.Exception.OrderException;
import com.SWP391.KoiXpress.Model.request.OrderDetailRequest;
import com.SWP391.KoiXpress.Model.request.OrderRequest;
import com.SWP391.KoiXpress.Model.request.OrderRequestCustomer;
import com.SWP391.KoiXpress.Model.response.OrderResponse;
import com.SWP391.KoiXpress.Model.response.OrderResponseAll;
import com.SWP391.KoiXpress.Model.response.UserResponse;
import com.SWP391.KoiXpress.Repository.OrderRepository;
import com.SWP391.KoiXpress.Repository.WareHouseRepository;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    BoxDetailService boxDetailService;

    @Autowired
    GeoCodingService geoCodingService;

    @Autowired
    RoutingService routingService;

    @Autowired
    WareHouseRepository wareHouseRepository;

    // Create
    public OrderResponse create(OrderRequest orderRequest) throws Exception {
        User user = authenticationService.getCurrentUser();
        Order order = new Order();
        double totalPrice = 0;
        double totalVolume = 0;
        int totalBox = 0;
        int totalQuantityFish = 0;

        order.setUser(user);

        order.setOrderDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));

        order.setDeliveryDate(Date.from(LocalDate.now().plusWeeks(4).atStartOfDay(ZoneId.systemDefault()).toInstant()));



        String destinationLocation = order.getDestinationLocation();
        String originLocation = orderRequest.getOriginLocation();

        double[] destination = geoCodingService.geocoding(destinationLocation);
        double[] originCoords = geoCodingService.geocoding(originLocation);
        String routeOD = routingService.getFormattedRoute(originCoords[0], originCoords[1], destination[0], destination[1]);
        double distanceOD = extractDistance(routeOD);
        order.setTotalDistance(distanceOD);

        //
        List<String> wareHouseRepositoryAllLocation = wareHouseRepository.findAllLocation();
        double minDistance = Double.MAX_VALUE;
        String nearestWareHouse = null;
        for (String wareHouse : wareHouseRepositoryAllLocation) {
            double[] wareHouseCoords = geoCodingService.geocoding(wareHouse);
            String routeInfo = routingService.getFormattedRoute(originCoords[0], originCoords[1], wareHouseCoords[0], wareHouseCoords[1]);
            double distance = extractDistance(routeInfo);
            // So sánh để tìm kho gần nhất
            if (distance < minDistance && distance != -1) {
                minDistance = distance; // Cập nhật khoảng cách nhỏ nhất
                nearestWareHouse = wareHouse; // Cập nhật kho gần nhất
            }
        }
        order.setNearWareHouse(nearestWareHouse);
        //

        order.setOriginLocation(orderRequest.getOriginLocation());
        order.setDestinationLocation(orderRequest.getDestinationLocation());
        order.setMethodTransPort(orderRequest.getMethodTransPort());
        order.setPaymentMethod(orderRequest.getPaymentMethod());
        order.setCustomerNotes(orderRequest.getCustomerNotes());
        order.setOrderStatus(OrderStatus.Pending);


        List<OrderDetail> orderDetails = new ArrayList<>();
        for (OrderDetailRequest orderDetailRequest : orderRequest.getOrderDetailRequestList()) {
            Map<Double, Integer> fishSizeQuantityMap = Map.of(orderDetailRequest.getSizeOfFish(), orderDetailRequest.getNumberOfFish());
            BoxDetail boxDetail = boxDetailService.createBox(fishSizeQuantityMap);
            OrderDetail orderDetail = new OrderDetail();

            orderDetail.setOrder(order);
            orderDetail.setBoxDetail(boxDetail);
            orderDetail.setPriceOfFish(orderDetailRequest.getPriceOfFish());
            orderDetail.setNameFarm(orderDetailRequest.getNameFarm());
            orderDetail.setFarmAddress(orderDetailRequest.getFarmAddress());
            orderDetail.setOrigin(orderDetailRequest.getOrigin());
            orderDetail.setRecipientInfo(orderDetailRequest.getRecipientInfo());
            orderDetail.setInspectionDate(Date.from(LocalDate.now().minusYears(1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
            orderDetail.setFishSpecies(orderDetailRequest.getFishSpecies());
            orderDetail.setNumberOfFish(orderDetailRequest.getNumberOfFish());
            orderDetail.setSizeOfFish(orderDetailRequest.getSizeOfFish());
            orderDetail.setHealthFishStatus(HealthFishStatus.Healthy);
            orderDetail.setOrderStatus(OrderStatus.Pending);
            orderDetail.setDescribeOrder(orderDetailRequest.getDescribeOrder());

            orderDetail.setPrice(boxDetail.getTotalPrice());
            orderDetail.setTotalBox(boxDetail.getTotalBox());
            orderDetail.setTotalVolume(boxDetail.getTotalVolume());
            orderDetails.add(orderDetail);

        }
        for (OrderDetail orderDetail : orderDetails) {
            totalPrice += orderDetail.getPrice(); // Cộng dồn giá
            totalBox += orderDetail.getTotalBox(); // Cộng dồn số lượng
            totalVolume += orderDetail.getTotalVolume();
            totalQuantityFish += orderDetail.getNumberOfFish();// Cộng dồn thể tích
        }
        order.setOrderDetails(orderDetails);
        order.setTotalPrice(totalPrice);
        order.setTotalBox(totalBox);
        order.setTotalVolume(totalVolume);
        order.setTotalQuantity(totalQuantityFish);
        orderRepository.save(order);
        return modelMapper.map(order, OrderResponse.class);
    }

    public List<OrderResponse> getAllOrdersByCurrentUser() {
        User user = authenticationService.getCurrentUser();
        List<Order> orders = orderRepository.findOrdersByUser(user);
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderResponse.class))
                .filter(order -> order.getOrderStatus() != OrderStatus.Canceled)
                .collect(Collectors.toList());
    }

    // Update
    public OrderResponse userUpdate(long id, OrderRequestCustomer orderRequest) throws Exception {
        Order oldOrder = getOrderById(id);
        if(oldOrder.getOrderStatus() == OrderStatus.Pending){
            oldOrder.setOriginLocation(orderRequest.getOriginLocation());
            oldOrder.setDestinationLocation(orderRequest.getDestinationLocation());

            String destinationLocation = orderRequest.getDestinationLocation();
            String originLocation = orderRequest.getOriginLocation();

            double[] destination = geoCodingService.geocoding(destinationLocation);
            double[] originCoords = geoCodingService.geocoding(originLocation);
            String routeOD = routingService.getFormattedRoute(originCoords[0], originCoords[1], destination[0], destination[1]);
            double distanceOD = extractDistance(routeOD);
            oldOrder.setTotalDistance(distanceOD);

            //
            List<String> wareHouseRepositoryAllLocation = wareHouseRepository.findAllLocation();
            double minDistance = Double.MAX_VALUE;
            String nearestWareHouse = null;
            for (String wareHouse : wareHouseRepositoryAllLocation) {
                double[] wareHouseCoords = geoCodingService.geocoding(wareHouse);
                String routeInfo = routingService.getFormattedRoute(originCoords[0], originCoords[1], wareHouseCoords[0], wareHouseCoords[1]);
                double distance = extractDistance(routeInfo);
                // So sánh để tìm kho gần nhất
                if (distance < minDistance && distance != -1) {
                    minDistance = distance; // Cập nhật khoảng cách nhỏ nhất
                    nearestWareHouse = wareHouse; // Cập nhật kho gần nhất
                }
            }
            oldOrder.setNearWareHouse(nearestWareHouse);
            //

            oldOrder.setCustomerNotes(orderRequest.getCustomerNotes());
            oldOrder.setPaymentMethod(orderRequest.getPaymentMethod());
            oldOrder.setMethodTransPort(orderRequest.getMethodTransPort());
            Order updatedOrder = orderRepository.save(oldOrder);
            return modelMapper.map(updatedOrder, OrderResponse.class);
        }
        throw new OrderException("Order can not update");
    }

    //Delete
    public OrderResponse delete(long id) {
        Order oldOrder = getOrderById(id);
        oldOrder.setOrderStatus(OrderStatus.Canceled);
        Order deletedOrder = orderRepository.save(oldOrder);
        return modelMapper.map(deletedOrder, OrderResponse.class);
    }


    private Order getOrderById(long id) {
        Order oldOrder = orderRepository.findOrderById(id);
        if (oldOrder == null || oldOrder.getOrderStatus() == OrderStatus.Canceled) {
            throw new EntityNotFoundException("Order not found");
        }
        return oldOrder;
    }


    public OrderResponse getEachOrderById(long id) {
        Order order = orderRepository.findOrderById(id);
        if (order == null) {
            throw new NotFoundException("Order not found");
        }
        return modelMapper.map(order, OrderResponse.class);
    }

    public List<OrderResponseAll> getAll() {
        List<Order> orders = orderRepository.findAll();
        List<OrderResponseAll> orderResponses = new ArrayList<>();
        for (Order order : orders) {
            UserResponse userResponse = modelMapper.map(order.getUser(), UserResponse.class);
            OrderResponseAll orderResponse = modelMapper.map(order, OrderResponseAll.class);
            orderResponse.setUserResponse(userResponse);
            orderResponses.add(orderResponse);
        }
        return orderResponses;
    }

    private double extractDistance(String routeInfo) {
        String[] lines = routeInfo.split("\n");
        String totalDistance = null;
        for (String line : lines) {
            if (line.startsWith("Total Distance: ")) {
                totalDistance = line;
                break;
            }
        }
        if (totalDistance != null) {
            String[] parts = totalDistance.split(":");
            if (parts.length > 1) {
                String distanceValue = parts[1].trim();
                return Double.parseDouble(distanceValue.split(" ")[0]);
            }
        }
        throw new EntityNotFoundException("Cant find distance");
    }

}
