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
        Box box = modelMapper.map(createBoxRequest, Box.class);
        CreateBoxResponse createBoxResponse = modelMapper.map(box, CreateBoxResponse.class);
        boxRepository.save(box);
        return createBoxResponse;
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
