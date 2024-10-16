package com.SWP391.KoiXpress.Api;

import com.SWP391.KoiXpress.Entity.Blog;
import com.SWP391.KoiXpress.Entity.FeedBack;
import com.SWP391.KoiXpress.Entity.FeedBackReply;
import com.SWP391.KoiXpress.Model.request.FeedBackRequet;
import com.SWP391.KoiXpress.Model.response.FeedBackResponse;
import com.SWP391.KoiXpress.Service.FeedBackService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/feedBack")
@CrossOrigin("*")
@SecurityRequirement(name = "api")
@PreAuthorize("hasAuthority('CUSTOMER') or hasAuthority('SALESSTAFF')")
public class FeedBackAPI {

    @Autowired
    FeedBackService feedBackService;

    @PostMapping
    public ResponseEntity createFeedBack(@Valid @RequestBody FeedBackRequet feedBackRequet) {
        FeedBack newFeedBack = feedBackService.createFeedBack(feedBackRequet);
        return ResponseEntity.ok(newFeedBack);
    }

    @PostMapping("/{feedBackId}/reply")
    @PreAuthorize("hasAuthority('SALESSTAFF')")
    public ResponseEntity replyToFeedBack(@PathVariable long feedBackId,
                                          @RequestBody String replyContent,
                                          Principal principal) {
        String repliedBy = principal.getName();
        FeedBackReply reply = feedBackService.replyToFeedBack(feedBackId, replyContent, repliedBy);
        return ResponseEntity.ok(reply);
    }

    @PreAuthorize("hasAuthority('SALESSTAFF')")
    @GetMapping("/user/{userId}/feedbacks")
    public ResponseEntity getFeedbacksByUser(@PathVariable Long userId) {
        List<FeedBackResponse> feedBacks = feedBackService.getAllFeedBacksByUser(userId);
        return ResponseEntity.ok(feedBacks);
    }


    @PreAuthorize("hasAuthority('SALESSTAFF') and (hasAuthority('Customer') and @feedBackService.isOwner(#feedBackId))")
    @PutMapping("/{feedBackId}")
    public ResponseEntity updateFeedBack(@PathVariable long feedBackId, @Valid @RequestBody FeedBackRequet feedBackRequet) {
        FeedBack newFeedBack = feedBackService.updateFeedBack(feedBackId, feedBackRequet);
        return ResponseEntity.ok(newFeedBack);
    }

    @PreAuthorize("hasAuthority('SALESSTAFF')")
    @GetMapping("/order/{orderId}/feedbacks")
    public ResponseEntity<List<FeedBackResponse>> getFeedbacksByOrder(@PathVariable Long orderId) {
        List<FeedBackResponse> feedBacks = feedBackService.getAllFeedBacksByOrder(orderId);
    @PostMapping("/{feedBackId}/reply")
    @PreAuthorize("hasAuthority('Sale_staff')")
    public ResponseEntity replyToFeedBack(@PathVariable long feedBackId,
                                          @RequestBody String replyContent,
                                          Principal principal) {
        String repliedBy = principal.getName();
        FeedBackReply reply = feedBackService.replyToFeedBack(feedBackId, replyContent, repliedBy);
        return ResponseEntity.ok(reply);
    }

    @PreAuthorize("hasAuthority('Sale_staff')")
    @GetMapping("/user/{userId}/feedbacks")
    public ResponseEntity getFeedbacksByUser(@PathVariable Long userId) {
        List<FeedBackResponse> feedBacks = feedBackService.getAllFeedBacksByUser(userId);
        return ResponseEntity.ok(feedBacks);
    }


    @PreAuthorize("hasAuthority('Sale_staff') and (hasAuthority('Customer') and @feedBackService.isOwner(#feedBackId))")
    @PutMapping("/{feedBackId}")
    public ResponseEntity updateFeedBack(@PathVariable long feedBackId, @Valid @RequestBody FeedBackRequet feedBackRequet) {
        FeedBack newFeedBack = feedBackService.updateFeedBack(feedBackId, feedBackRequet);
        return ResponseEntity.ok(newFeedBack);
    }

    @PreAuthorize("hasAuthority('Sale_staff')")
    @GetMapping("/order/{orderId}/feedbacks")
    public ResponseEntity<List<FeedBackResponse>> getFeedbacksByOrder(@PathVariable Long orderId) {
        List<FeedBackResponse> feedBacks = feedBackService.getAllFeedBacksByOrder(orderId);
        return ResponseEntity.ok(feedBacks);
    }
    @PreAuthorize("hasAuthority('Customer')")
    @GetMapping("/my-feedbacks")
    public ResponseEntity<List<FeedBackResponse>> getMyFeedbacks() {
        List<FeedBackResponse> feedBacks = feedBackService.getAllFeedBacksByCurrentUser();
        return ResponseEntity.ok(feedBacks);
    }


    @PreAuthorize("hasAuthority('Sale_staff') or (hasAuthority('Customer') and @feedBackService.isOwner(#feedBackId))")
    @DeleteMapping("/{feedBackId}")
    public ResponseEntity deleteFeedBack(@PathVariable long feedBackId) {
        feedBackService.deleteFeedBack(feedBackId);
        return ResponseEntity.ok().build();
    }
}
