package com.SWP391.KoiXpress.Service;

import com.SWP391.KoiXpress.Entity.WareHouse;
import com.SWP391.KoiXpress.Exception.ProgressException;
import com.SWP391.KoiXpress.Model.request.WareHouse.CreateWareHouseRequest;
import com.SWP391.KoiXpress.Repository.OrderRepository;
import com.SWP391.KoiXpress.Repository.WareHouseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WareHouseService {

    @Autowired
    WareHouseRepository wareHouseRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ModelMapper modelMapper;

    public WareHouse create(CreateWareHouseRequest createWareHouseRequest){
        WareHouse wareHouse = new WareHouse();
        wareHouse.setLocation(createWareHouseRequest.getLocation());
        return wareHouseRepository.save(wareHouse);
    }

    public void delete(long id){
        WareHouse wareHouse = wareHouseRepository.findWareHouseById(id);
        wareHouse.setAvailable(false);
        wareHouseRepository.save(wareHouse);
    }

}
