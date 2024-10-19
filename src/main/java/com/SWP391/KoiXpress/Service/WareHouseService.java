package com.SWP391.KoiXpress.Service;

import com.SWP391.KoiXpress.Entity.WareHouse;
import com.SWP391.KoiXpress.Exception.ProgressException;
import com.SWP391.KoiXpress.Model.request.WareHouse.CreateWareHouseRequest;
import com.SWP391.KoiXpress.Model.response.CreateWarehouseResponse;
import com.SWP391.KoiXpress.Repository.OrderRepository;
import com.SWP391.KoiXpress.Repository.WareHouseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WareHouseService {

    @Autowired
    WareHouseRepository wareHouseRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ModelMapper modelMapper;

    public CreateWarehouseResponse create(CreateWareHouseRequest createWareHouseRequest){
        WareHouse wareHouse = new WareHouse();
        wareHouse.setLocation(createWareHouseRequest.getLocation());
        wareHouseRepository.save(wareHouse);
        return modelMapper.map(wareHouse, CreateWarehouseResponse.class);
    }

    public void delete(long id){
        WareHouse wareHouse = wareHouseRepository.findWareHouseById(id);
        wareHouse.setAvailable(false);
        wareHouseRepository.save(wareHouse);
    }

    public List<WareHouse> getAllWareHouseAvailable(){
        return wareHouseRepository.findByIsAvailableTrue();
    }

    public List<WareHouse> getAllWareHouseNotAvailable(){
        return wareHouseRepository.findByIsAvailableFalse();
    }
}
