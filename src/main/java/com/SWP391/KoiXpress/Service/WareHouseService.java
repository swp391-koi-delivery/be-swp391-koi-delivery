package com.SWP391.KoiXpress.Service;

import com.SWP391.KoiXpress.Entity.Order;
import com.SWP391.KoiXpress.Entity.WareHouse;
import com.SWP391.KoiXpress.Model.request.OrderWareHouseRequest;
import com.SWP391.KoiXpress.Repository.OrderRepository;
import com.SWP391.KoiXpress.Repository.WareHouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WareHouseService {

    @Autowired
    WareHouseRepository wareHouseRepository;

    @Autowired
    OrderRepository orderRepository;

    public WareHouse create(WareHouse wareHouse){
        return  wareHouseRepository.save(wareHouse);
    }

    public List<Order> putOrderIntoWarehouse(OrderWareHouseRequest orderWareHouseRequest){
        List<Order> orders = orderRepository.findOrdersByNearWareHouse(orderWareHouseRequest.getLocation());
        return null;
    }
}
