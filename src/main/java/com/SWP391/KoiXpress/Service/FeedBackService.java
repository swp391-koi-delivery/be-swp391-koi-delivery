package com.SWP391.KoiXpress.Service;

import com.SWP391.KoiXpress.Entity.Blog;
import com.SWP391.KoiXpress.Entity.FeedBack;
import com.SWP391.KoiXpress.Entity.User;
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

    public FeedBack createFeedBack(FeedBackRequet feedBackRequet){
         FeedBack feedBack = new FeedBack();
         feedBack.setComment(feedBackRequet.getComment());
         feedBack.setRatingScore(feedBackRequet.getRatingScore());
        return feedBackRepository.save(feedBack);
    }

    public List<FeedBack> getAllFeedBack() {
        List<FeedBack> feedBacks = feedBackRepository.findAll();
        return feedBacks;
    }
}
