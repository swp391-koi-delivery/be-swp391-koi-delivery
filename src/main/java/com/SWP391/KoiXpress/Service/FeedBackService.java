package com.SWP391.KoiXpress.Service;

import com.SWP391.KoiXpress.Entity.Blog;
import com.SWP391.KoiXpress.Entity.FeedBack;
import com.SWP391.KoiXpress.Entity.User;
import com.SWP391.KoiXpress.Exception.EntityNotFoundException;
import com.SWP391.KoiXpress.Model.request.BlogRequest;
import com.SWP391.KoiXpress.Model.request.FeedBackRequet;
import com.SWP391.KoiXpress.Repository.FeedBackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedBackService {

    @Autowired
    FeedBackRepository feedBackRepository;

    public FeedBack createFeedBack(FeedBackRequet feedBackRequet) {
        FeedBack feedBack = new FeedBack();
        feedBack.setComment(feedBackRequet.getComment());
        feedBack.setRatingScore(feedBackRequet.getRatingScore());
        return feedBackRepository.save(feedBack);
    }

    public List<FeedBack> getAllFeedBack() {
        List<FeedBack> feedBacks = feedBackRepository.findAll();
        return feedBacks;
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
            throw new EntityNotFoundException("not found feedback");
        }
    }

    private FeedBack getFeedById(long FeedId) {
        FeedBack oldFeedBack = feedBackRepository.findByFeedId(FeedId);
        if (oldFeedBack == null) {
            throw new EntityNotFoundException("FeedBack not found!");
        }
        return oldFeedBack;
    }
    //---------------------------------------------------------------------



}
