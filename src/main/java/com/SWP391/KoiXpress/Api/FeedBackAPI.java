package com.SWP391.KoiXpress.Api;

import com.SWP391.KoiXpress.Entity.Blog;
import com.SWP391.KoiXpress.Entity.FeedBack;
import com.SWP391.KoiXpress.Model.request.BlogRequest;
import com.SWP391.KoiXpress.Model.request.FeedBackRequet;
import com.SWP391.KoiXpress.Service.FeedBackService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/FeedBack")
@CrossOrigin("*")
@SecurityRequirement(name="api")
public class FeedBackAPI {

    @Autowired
    FeedBackService feedBackService;
    @PostMapping
    public ResponseEntity createFeedBack(@Valid @RequestBody FeedBackRequet feedBackRequet){
        FeedBack newFeedBack = feedBackService.createFeedBack(feedBackRequet);
        return ResponseEntity.ok(newFeedBack);
    }

    @GetMapping
    public  ResponseEntity get(){
        List<FeedBack> feedBacks = feedBackService.getAllFeedBack();
        return ResponseEntity.ok(feedBacks);
    }

    @PutMapping("/{feedBackId}")
    public ResponseEntity updateFeedBack( @PathVariable long feedId,@Valid @RequestBody FeedBackRequet feedBackRequet){
        FeedBack newFeedBack = feedBackService.updateFeedBack( feedId,feedBackRequet);
        return ResponseEntity.ok(newFeedBack);
    }

    @DeleteMapping("/{feedBackId}")
    public ResponseEntity deleteFeedBack(@PathVariable long feedBackId){
        feedBackService.deleteFeedBack(feedBackId);
        return ResponseEntity.ok().build();
    }
    
}
