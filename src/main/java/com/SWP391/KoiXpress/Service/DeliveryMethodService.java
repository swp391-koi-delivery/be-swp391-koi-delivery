package com.SWP391.KoiXpress.Service;

import com.SWP391.KoiXpress.Repository.DeliveryMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryMethodService {

    @Autowired
    DeliveryMethodRepository deliveryMethodRepository;
}
