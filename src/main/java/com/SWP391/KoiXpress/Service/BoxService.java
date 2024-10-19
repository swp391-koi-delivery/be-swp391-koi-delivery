package com.SWP391.KoiXpress.Service;

import com.SWP391.KoiXpress.Entity.Box;
import com.SWP391.KoiXpress.Exception.BoxException;
import com.SWP391.KoiXpress.Model.request.Box.CreateBoxRequest;
import com.SWP391.KoiXpress.Model.response.Box.CreateBoxResponse;
import com.SWP391.KoiXpress.Repository.BoxRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoxService {

    @Autowired
    BoxRepository boxRepository;

    @Autowired
    ModelMapper modelMapper;

    public CreateBoxResponse create(CreateBoxRequest createBoxRequest){
        Box box = new Box();
        box.setType(createBoxRequest.getType());
        box.setVolume(createBoxRequest.getVolume());
        box.setPrice(createBoxRequest.getPrice());
        boxRepository.save(box);
        return modelMapper.map(box, CreateBoxResponse.class);
    }


    private Box findBoxById(long id){
        Box box = boxRepository.findBoxById(id);
        if(box != null){
            return box;
        }else{
            throw new BoxException("Box doesn't exist");
        }
    }
}
