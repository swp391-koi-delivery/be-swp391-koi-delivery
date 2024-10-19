package com.SWP391.KoiXpress.Service;

import com.SWP391.KoiXpress.Entity.DeliveryMethod;
import com.SWP391.KoiXpress.Exception.NotFoundException;
import com.SWP391.KoiXpress.Model.request.DeliveryMethod.CreateDeliveryMethodRequest;
import com.SWP391.KoiXpress.Model.request.DeliveryMethod.UpdateDeliveryMethodRequest;
import com.SWP391.KoiXpress.Repository.DeliveryMethodRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryMethodService {

    @Autowired
    DeliveryMethodRepository deliveryMethodRepository;

    @Autowired
    ModelMapper modelMapper;

    public DeliveryMethod create(CreateDeliveryMethodRequest createDeliveryMethodRequest){
        DeliveryMethod deliveryMethod = modelMapper.map(createDeliveryMethodRequest, DeliveryMethod.class);
        return deliveryMethodRepository.save(deliveryMethod);
    }
    public DeliveryMethod update(long id,UpdateDeliveryMethodRequest updateDeliveryMethodRequest){
        DeliveryMethod oldDelivery = findDeliveryById(id);
        oldDelivery.setTypeVehicle(updateDeliveryMethodRequest.getTypeVehicle());
        oldDelivery.setPrice(updateDeliveryMethodRequest.getPrice());
        return deliveryMethodRepository.save(oldDelivery);
    }
    public void delete(long id){
        DeliveryMethod deliveryMethod = findDeliveryById(id);
        deliveryMethodRepository.delete(deliveryMethod);
    }

    public List<DeliveryMethod> getAll(){
        List<DeliveryMethod> deliveryMethods = deliveryMethodRepository.findAll();
        return deliveryMethods;
    }

    private DeliveryMethod findDeliveryById(long id){
        DeliveryMethod deliveryMethod = deliveryMethodRepository.findDeliveryMethodById(id);
        if(deliveryMethod != null){
            return deliveryMethod;
        }
        else{
            throw new NotFoundException("Can not found DeliveryMethod");
        }
    }
}
