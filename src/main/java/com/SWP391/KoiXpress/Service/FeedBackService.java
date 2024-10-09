package com.SWP391.KoiXpress.Service;

import com.SWP391.KoiXpress.Entity.FeedBack;
import com.SWP391.KoiXpress.Entity.FeedBackReply;
import com.SWP391.KoiXpress.Entity.User;
import com.SWP391.KoiXpress.Exception.EntityNotFoundException;
import com.SWP391.KoiXpress.Model.request.FeedBackRequet;
import com.SWP391.KoiXpress.Model.response.FeedBackReplyResponse;
import com.SWP391.KoiXpress.Model.response.FeedBackResponse;
import com.SWP391.KoiXpress.Model.response.UserResponse;
import com.SWP391.KoiXpress.Repository.FeedBackReplyRepository;
import com.SWP391.KoiXpress.Repository.FeedBackRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedBackService {

    @Autowired
    FeedBackRepository feedBackRepository;
    @Autowired
    FeedBackReplyRepository feedBackReplyRepository;
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    private ModelMapper modelMapper;

    public FeedBack createFeedBack(FeedBackRequet feedBackRequet) {
        User user = authenticationService.getCurrentUser();
        FeedBack feedBack = new FeedBack();
        feedBack.setUser(user);
        feedBack.setComment(feedBackRequet.getComment());
        feedBack.setRatingScore(feedBackRequet.getRatingScore());
        feedBack.setCreatedTime(LocalDateTime.now());
        return feedBackRepository.save(feedBack);
    }

    public List<FeedBackResponse> getAllFeedBack() {
        List<FeedBackResponse> feedBackResponses = new ArrayList<>();
        List<FeedBack> feedBacks = feedBackRepository.findAll();

        for (FeedBack feedBack : feedBacks) {
            User user = feedBack.getUser();
            FeedBackResponse feedBackResponse = new FeedBackResponse();
            UserResponse userResponse = modelMapper.map(user, UserResponse.class);

            feedBackResponse.setFeedbackId(feedBack.getFeedBackId());
            feedBackResponse.setRatingScore(feedBack.getRatingScore());
            feedBackResponse.setComment(feedBack.getComment());
            feedBackResponse.setUserResponse(userResponse);
            feedBackResponse.setCreatedTime(feedBack.getCreatedTime());

            // Ánh xạ danh sách phản hồi (replies)
            List<FeedBackReplyResponse> replyResponses = feedBack.getReplies().stream()
                    .map(reply -> {
                        FeedBackReplyResponse replyResponse = new FeedBackReplyResponse();
                        replyResponse.setReplyContent(reply.getReplyContent());
                        replyResponse.setRepliedBy(reply.getRepliedBy());
                        replyResponse.setReplyDate(reply.getReplyDate());
                        return replyResponse;
                    }).collect(Collectors.toList());

            feedBackResponse.setReplies(replyResponses);

            feedBackResponses.add(feedBackResponse);
        }

        return feedBackResponses;
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
            feedBack.setDeleted(true);
            feedBackRepository.save(feedBack);
        } catch (Exception e) {
            e.printStackTrace();
            throw new EntityNotFoundException("Not found feedback");
        }
    }

    public FeedBackReply replyToFeedBack(long feedBackId, String replyContent, String repliedBy) {

        FeedBack feedBack = feedBackRepository.findById(feedBackId)
                .orElseThrow(() -> new EntityNotFoundException("Feedback not found"));

        // Tạo mới một FeedBackReply
        FeedBackReply feedBackReply = new FeedBackReply();

        feedBackReply.setFeedBack(feedBack);
        feedBackReply.setReplyContent(replyContent);
        feedBackReply.setRepliedBy(repliedBy);
        feedBackReply.setReplyDate(LocalDateTime.now());

        feedBackReply = feedBackReplyRepository.save(feedBackReply);

        // Thêm reply vào danh sách replies của feedback
        feedBack.getReplies().add(feedBackReply);

        // Lưu feedback cùng với replies
        feedBackRepository.save(feedBack);

        return feedBackReply;
    }

    private FeedBack getFeedById(long FeedId) {
        FeedBack oldFeedBack = feedBackRepository.findByFeedBackId(FeedId);
        if (oldFeedBack == null) {
            throw new EntityNotFoundException("FeedBack not found!");
        }
        return oldFeedBack;
    }
    //---------------------------------------------------------------------


}
