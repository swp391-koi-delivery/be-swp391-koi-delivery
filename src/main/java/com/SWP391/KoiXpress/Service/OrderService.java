package com.SWP391.KoiXpress.Service;


import com.SWP391.KoiXpress.Entity.*;
import com.SWP391.KoiXpress.Entity.Enum.HealthFishStatus;
import com.SWP391.KoiXpress.Entity.Enum.OrderStatus;
import com.SWP391.KoiXpress.Exception.EntityNotFoundException;
import com.SWP391.KoiXpress.Exception.NotFoundException;
import com.SWP391.KoiXpress.Exception.OrderException;
import com.SWP391.KoiXpress.Model.request.Order.OrderDetailRequest;
import com.SWP391.KoiXpress.Model.request.Order.CreateOrderRequest;
import com.SWP391.KoiXpress.Model.request.Order.UpdateOrderRequest;
import com.SWP391.KoiXpress.Model.response.Box.CreateBoxDetailResponse;
import com.SWP391.KoiXpress.Model.response.Order.*;
import com.SWP391.KoiXpress.Model.response.User.EachUserResponse;
import com.SWP391.KoiXpress.Model.response.User.UserResponse;
import com.SWP391.KoiXpress.Repository.OrderDetailRepository;
import com.SWP391.KoiXpress.Repository.OrderRepository;
import com.SWP391.KoiXpress.Repository.WareHouseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
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

    @Autowired
    OrderDetailRepository orderDetailRepository;

    // Create
    public CreateOrderResponse create(CreateOrderRequest createOrderRequest) throws Exception {
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
        String originLocation = createOrderRequest.getOriginLocation();

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
        order.setRecipientInfo(createOrderRequest.getRecipientInfo());
        order.setOriginLocation(createOrderRequest.getOriginLocation());
        order.setDestinationLocation(createOrderRequest.getDestinationLocation());
        order.setMethodTransPort(createOrderRequest.getMethodTransPort());
        order.setPaymentMethod(createOrderRequest.getPaymentMethod());
        order.setCustomerNotes(createOrderRequest.getCustomerNotes());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setDescribeOrder(createOrderRequest.getDescribeOrder());
        orderRepository.save(order);

        List<OrderDetail> orderDetails = new ArrayList<>();
        for (OrderDetailRequest orderDetailRequest : createOrderRequest.getOrderDetailRequestList()) {
            Map<Double, Integer> fishSizeQuantityMap = Map.of(orderDetailRequest.getSizeOfFish(), orderDetailRequest.getNumberOfFish());
            OrderDetail orderDetail = new OrderDetail();

            orderDetail.setOrder(order);
            orderDetail.setPriceOfFish(orderDetailRequest.getPriceOfFish());
            orderDetail.setNameFarm(orderDetailRequest.getNameFarm());
            orderDetail.setFarmAddress(orderDetailRequest.getFarmAddress());
            orderDetail.setOrigin(orderDetailRequest.getOrigin());
            orderDetail.setInspectionDate(Date.from(LocalDate.now().minusYears(1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
            orderDetail.setFishSpecies(orderDetailRequest.getFishSpecies());
            orderDetail.setNumberOfFish(orderDetailRequest.getNumberOfFish());
            orderDetail.setSizeOfFish(orderDetailRequest.getSizeOfFish());
            orderDetail.setHealthFishStatus(HealthFishStatus.HEALTHY);

            orderDetailRepository.save(orderDetail);
            CreateBoxDetailResponse boxDetails = boxDetailService.createBox(fishSizeQuantityMap, orderDetail);

            orderDetail.setBoxDetails(boxDetails.getBoxDetails());
            orderDetail.setPrice(boxDetails.getTotalPrice());
            orderDetail.setTotalBox(boxDetails.getTotalCount());
            orderDetail.setTotalVolume(boxDetails.getTotalVolume());

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

        return modelMapper.map(order, CreateOrderResponse.class);
    }

    public List<AllOrderByCurrentResponse> getAllOrdersByCurrentUser() {
        User user = authenticationService.getCurrentUser();
        List<Order> orders = orderRepository.findOrdersByUser(user);
        return orders.stream()
                .map(order -> modelMapper.map(order, AllOrderByCurrentResponse.class))
                .filter(order -> order.getOrderStatus() != OrderStatus.CANCEL)
                .collect(Collectors.toList());
    }

    //Sale update
    public UpdateOrderResponse updateBySale(long id, UpdateOrderRequest updateOrderRequest){
        Order oldOrder = getOrderById(id);
        if(oldOrder.getOrderStatus() == OrderStatus.PENDING){
            oldOrder.setOrderStatus(updateOrderRequest.getOrderStatus());
            Order newOrder = orderRepository.save(oldOrder);
            return  modelMapper.map(newOrder, UpdateOrderResponse.class);
        }else {
            throw new OrderException("Can not update");
        }
    }
  //list nhung order dang pending

    //

    //Delete
    public DeleteOrderResponse delete(long id) {
        Order oldOrder = getOrderById(id);
        oldOrder.setOrderStatus(OrderStatus.CANCEL);
        Order deletedOrder = orderRepository.save(oldOrder);
        return modelMapper.map(deletedOrder, DeleteOrderResponse.class);
    }


    private Order getOrderById(long id) {
        Order oldOrder = orderRepository.findOrderById(id);
        if (oldOrder == null || oldOrder.getOrderStatus() == OrderStatus.CANCEL) {
            throw new EntityNotFoundException("Order not found");
        }
        return oldOrder;
    }


    public CreateOrderResponse getEachOrderById(long id) {
        Order order = orderRepository.findOrderById(id);
        if (order == null) {
            throw new NotFoundException("Order not found");
        }
        return modelMapper.map(order, CreateOrderResponse.class);
    }

    public List<AllOrderResponse> getAll() {
        List<Order> orders = orderRepository.findAll();
        List<AllOrderResponse> orderResponses = new ArrayList<>();
        for (Order order : orders) {
            UserResponse userResponse = modelMapper.map(order.getUser(), UserResponse.class);
            AllOrderResponse orderResponse = modelMapper.map(order, AllOrderResponse.class);
//            orderResponse.setOrderDetails(order.getOrderDetails().stream().map(item -> modelMapper.map(item, OrderDetailResponse.class)).toList());
            orderResponse.setEachUserResponse(userResponse);
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
