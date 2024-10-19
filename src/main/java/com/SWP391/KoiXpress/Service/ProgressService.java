package com.SWP391.KoiXpress.Service;

import com.SWP391.KoiXpress.Entity.*;
import com.SWP391.KoiXpress.Entity.Enum.HealthFishStatus;
import com.SWP391.KoiXpress.Entity.Enum.OrderStatus;
import com.SWP391.KoiXpress.Entity.Enum.ProgressStatus;
import com.SWP391.KoiXpress.Exception.NotFoundException;
import com.SWP391.KoiXpress.Exception.ProgressException;
import com.SWP391.KoiXpress.Model.request.Progress.ProgressRequest;
import com.SWP391.KoiXpress.Model.request.Progress.UpdateProgressRequest;
import com.SWP391.KoiXpress.Model.response.Progress.DeleteProgressResponse;
import com.SWP391.KoiXpress.Model.response.Progress.ProgressResponse;
import com.SWP391.KoiXpress.Model.response.Progress.UpdateProgressResponse;
import com.SWP391.KoiXpress.Repository.OrderRepository;
import com.SWP391.KoiXpress.Repository.ProgressRepository;
import com.SWP391.KoiXpress.Repository.WareHouseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProgressService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProgressRepository progressRepository;

    @Autowired
    WareHouseRepository wareHouseRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    WareHouseService wareHouseService;

    public List<ProgressResponse> create(ProgressRequest progressRequest) {
        Order order = orderRepository.findOrderById(progressRequest.getOrderId());
        List<Progress> existProgressOrder = progressRepository.findProgressesByOrderId(progressRequest.getOrderId());
        if (!existProgressOrder.isEmpty()) {
            throw new ProgressException("Order already in another Progress");
        }
        if (order == null) {
            throw new NotFoundException("Can not found order");
        }
        if (order.getOrderStatus() == OrderStatus.SHIPPING) {
            List<ProgressResponse> progresses = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                Progress progress = new Progress();
                progress.setOrder(order);
                progressRepository.save(progress);
                ProgressResponse response = new ProgressResponse();
                response.setId(progress.getId());
                progresses.add(response);
            }
            return progresses;
        }
        throw new ProgressException("Order is not ready to ship");
    }

    public List<ProgressResponse> trackingOrder(UUID trackingOrder) {
        List<Progress> progresses = progressRepository.findProgressesByOrderIdAndStatusNotNull(trackingOrder);
        if (progresses != null) {
            return progresses.stream().map(progress -> modelMapper.map(progress, ProgressResponse.class)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public UpdateProgressResponse update(long id, UpdateProgressRequest updateProgressRequest) {
        Progress oldProgress = progressRepository.findProgressesById(id);
        Order order = progressRepository.findOrderByOrderId(oldProgress.getOrder());
        WareHouse wareHouse = wareHouseRepository.findWareHouseByLocation(order.getNearWareHouse());

        if (wareHouse != null && wareHouse.isAvailable()) {
            oldProgress.setDateProgress(new Date());
            oldProgress.setHealthFishStatus(updateProgressRequest.getHealthFishStatus());
            if(updateProgressRequest.getHealthFishStatus()== HealthFishStatus.UNHEALTHY){
                oldProgress.setProgressStatus(ProgressStatus.CANCEL);
                progressRepository.save(oldProgress);
                return modelMapper.map(oldProgress, UpdateProgressResponse.class);
            }
            oldProgress.setImage(updateProgressRequest.getImage());
            oldProgress.setProgressStatus(updateProgressRequest.getProgressStatus());
            if(updateProgressRequest.getProgressStatus() != null){
                oldProgress.setInProgress(true);
            }
            oldProgress.setWareHouse(wareHouse);
            wareHouseRepository.save(wareHouse);
            progressRepository.save(oldProgress);
            return modelMapper.map(oldProgress, UpdateProgressResponse.class);
        }
        throw new ProgressException("Can not update");
    }

    public DeleteProgressResponse delete(long id) {
        Progress progress = progressRepository.findProgressesById(id);
        progress.setInProgress(false);
        progress.setProgressStatus(ProgressStatus.CANCEL);
        progressRepository.save(progress);
        return modelMapper.map(progress, DeleteProgressResponse.class);
    }

}