package com.SWP391.KoiXpress.Service;

import com.SWP391.KoiXpress.Entity.Box;
import com.SWP391.KoiXpress.Model.request.BoxRequest;
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

    public Box create(BoxRequest boxRequest){
        Box box = modelMapper.map(boxRequest, Box.class);
        return boxRepository.save(box);
    }
}
