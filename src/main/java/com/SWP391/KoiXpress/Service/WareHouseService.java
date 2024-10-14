package com.SWP391.KoiXpress.Service;

import com.SWP391.KoiXpress.Entity.WareHouse;
import com.SWP391.KoiXpress.Repository.WareHouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WareHouseService {

    @Autowired
    WareHouseRepository wareHouseRepository;

    public WareHouse create(WareHouse wareHouse){
        WareHouse newWareHouse = wareHouseRepository.save(wareHouse);
        return newWareHouse;
    }
}
