package com.SWP391.KoiXpress.Service;

import com.SWP391.KoiXpress.Entity.*;
import com.SWP391.KoiXpress.Exception.EntityNotFoundException;
import com.SWP391.KoiXpress.Model.request.FeedBack.FeedBackRequet;
import com.SWP391.KoiXpress.Model.response.FeedBack.FeedBackReplyResponse;
import com.SWP391.KoiXpress.Model.response.FeedBack.FeedBackResponse;
import com.SWP391.KoiXpress.Model.response.Paging.PagedResponse;
import com.SWP391.KoiXpress.Model.response.User.UserResponse;
import com.SWP391.KoiXpress.Repository.FeedBackReplyRepository;
import com.SWP391.KoiXpress.Repository.FeedBackRepository;
import com.SWP391.KoiXpress.Repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedBackService {

    @Autowired
    FeedBackRepository feedBackRepository;
    @Autowired
    FeedBackReplyRepository feedBackReplyRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    private ModelMapper modelMapper;



    public FeedBack createFeedBack(FeedBackRequet feedBackRequet) {

        User user = authenticationService.getCurrentUser();

        Order order = orderRepository.findById(feedBackRequet.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));


        FeedBack feedBack = new FeedBack();
        feedBack.setUser(user);
        feedBack.setOrder(order);
        feedBack.setComment(feedBackRequet.getComment());
        feedBack.setRatingScore(feedBackRequet.getRatingScore());

        feedBack.setCreatedTime(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));

        return feedBackRepository.save(feedBack);
    }





    public FeedBack updateFeedBack(long FeedId, FeedBackRequet feedBackRequet) {
        FeedBack oldFeedback = getFeedById(FeedId);
        try {
            oldFeedback.setComment(feedBackRequet.getComment());
            oldFeedback.setRatingScore(feedBackRequet.getRatingScore());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return feedBackRepository.save(oldFeedback);
    }

    public void deleteFeedBack(long FeedId) {
        try {
            FeedBack feedBack = getFeedById(FeedId);
            feedBack.setDelete(true);
            feedBackRepository.save(feedBack);
        } catch (Exception e) {
            e.printStackTrace();
            throw new EntityNotFoundException("Not found feedback");
        }
    }

    public FeedBackReply replyToFeedBack(long Id, String replyContent, String repliedBy) {

        FeedBack feedBack =getFeedById(Id);


        FeedBackReply feedBackReply = new FeedBackReply();

        feedBackReply.setFeedBack(feedBack);
        feedBackReply.setReplyContent(replyContent);
        feedBackReply.setRepliedBy(repliedBy);
        feedBackReply.setReplyDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));

        feedBackReply = feedBackReplyRepository.save(feedBackReply);


        feedBack.getReplies().add(feedBackReply);


        feedBackRepository.save(feedBack);

        return feedBackReply;
    }

    public PagedResponse<FeedBackResponse> getAllFeedBacksByUser(Long userId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<FeedBack> feedBackPage = feedBackRepository.findFeedBacksByUserId(userId, pageRequest);

        List<FeedBackResponse> feedBackResponses = new ArrayList<>();
        for (FeedBack feedBack : feedBackPage.getContent()) {
            User user = feedBack.getUser();
            FeedBackResponse feedBackResponse = new FeedBackResponse();
            UserResponse userResponse = modelMapper.map(user, UserResponse.class);

            feedBackResponse.setId(feedBack.getId());
            feedBackResponse.setRatingScore(feedBack.getRatingScore());
            feedBackResponse.setComment(feedBack.getComment());
            feedBackResponse.setUserResponse(userResponse);
            feedBackResponse.setCreatedTime(feedBack.getCreatedTime());

            List<FeedBackReplyResponse> replyResponses = feedBack.getReplies().stream()
                    .map(reply -> {
                        FeedBackReplyResponse replyResponse = new FeedBackReplyResponse();
                        replyResponse.setReplyContent(reply.getReplyContent());
                        return replyResponse;
                    }).collect(Collectors.toList());

            feedBackResponse.setReplies(replyResponses);
            feedBackResponses.add(feedBackResponse);
        }

        return new PagedResponse<>(
                feedBackResponses,
                page,
                size,
                feedBackPage.getTotalElements(),
                feedBackPage.getTotalPages(),
                feedBackPage.isLast()
        );
    }


    public List<FeedBackResponse> getAllFeedBacksByOrder(Long orderId) {
        List<FeedBack> feedBacks = feedBackRepository.findFeedBacksByOrderId(orderId);

        List<FeedBackResponse> feedBackResponses = new ArrayList<>();
        for (FeedBack feedBack : feedBacks) {
            User user = feedBack.getUser();
            FeedBackResponse feedBackResponse = new FeedBackResponse();
            UserResponse userResponse = modelMapper.map(user, UserResponse.class);

            feedBackResponse.setId(feedBack.getId());
            feedBackResponse.setRatingScore(feedBack.getRatingScore());
            feedBackResponse.setComment(feedBack.getComment());
            feedBackResponse.setUserResponse(userResponse);
            feedBackResponse.setCreatedTime(feedBack.getCreatedTime());

            List<FeedBackReplyResponse> replyResponses = feedBack.getReplies().stream()
                    .map(reply -> {
                        FeedBackReplyResponse replyResponse = new FeedBackReplyResponse();
                        replyResponse.setReplyContent(reply.getReplyContent());
                        return replyResponse;
                    }).collect(Collectors.toList());

            feedBackResponse.setReplies(replyResponses);
            feedBackResponses.add(feedBackResponse);
        }

        return feedBackResponses;
    }

    public PagedResponse<FeedBackResponse> getAllFeedBacksByCurrentUser(int page, int size) {
        User user = authenticationService.getCurrentUser();
        if (user != null) {
            return getAllFeedBacksByUser(user.getId(), page, size);
        }
        return new PagedResponse<>(new ArrayList<>(), page, size, 0, 0, true);
    }


    private FeedBack getFeedById(long Id) {
        FeedBack oldFeedBack = feedBackRepository.findById(Id);
        if (oldFeedBack == null) {
            throw new EntityNotFoundException("FeedBack not found!");
        }
        return oldFeedBack;
    }
    //---------------------------------------------------------------------
//jij

}
