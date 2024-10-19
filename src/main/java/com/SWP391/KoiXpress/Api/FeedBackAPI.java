package com.SWP391.KoiXpress.Api;

import com.SWP391.KoiXpress.Entity.Blog;
import com.SWP391.KoiXpress.Entity.FeedBack;
import com.SWP391.KoiXpress.Entity.FeedBackReply;
import com.SWP391.KoiXpress.Model.request.FeedBack.FeedBackRequet;
import com.SWP391.KoiXpress.Model.response.FeedBack.FeedBackResponse;
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
@PreAuthorize("hasAuthority('CUSTOMER') or hasAuthority('SALE_STAFF')")
public class FeedBackAPI {

    @Autowired
    FeedBackService feedBackService;

    @PostMapping
    public ResponseEntity createFeedBack(@Valid @RequestBody FeedBackRequet feedBackRequet) {
        FeedBack newFeedBack = feedBackService.createFeedBack(feedBackRequet);
        return ResponseEntity.ok(newFeedBack);
    }

    @PostMapping("/{feedBackId}/reply")
    @PreAuthorize("hasAuthority('SALE_STAFF')")
    public ResponseEntity replyToFeedBack(@PathVariable long feedBackId,
                                          @RequestBody String replyContent,
                                          Principal principal) {
        String repliedBy = principal.getName();
        FeedBackReply reply = feedBackService.replyToFeedBack(feedBackId, replyContent, repliedBy);
        return ResponseEntity.ok(reply);
    }

    @PreAuthorize("hasAuthority('SALE_STAFF')")
    @GetMapping("/user/{userId}/feedbacks")
    public List<FeedBackResponse> getFeedBacksByUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return feedBackService.getAllFeedBacksByUser(userId, page-1, size);
    }


    @PreAuthorize("hasAuthority('SALE_STAFF') and (hasAuthority('CUSTOMER') and @feedBackService.isOwner(#feedBackId))")
    @PutMapping("/{feedBackId}")
    public ResponseEntity updateFeedBack(@PathVariable long feedBackId, @Valid @RequestBody FeedBackRequet feedBackRequet) {
        FeedBack newFeedBack = feedBackService.updateFeedBack(feedBackId, feedBackRequet);
        return ResponseEntity.ok(newFeedBack);
    }

    @PreAuthorize("hasAuthority('SALE_STAFF')")
    @GetMapping("/order/{orderId}/feedbacks")
    public List<FeedBackResponse> getFeedBacksByOrder(
            @PathVariable Long orderId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return feedBackService.getAllFeedBacksByOrder(orderId, page-1, size);
    }
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping("/my-feedbacks")
    public List<FeedBackResponse> getFeedBacksByCurrentUser(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return feedBackService.getAllFeedBacksByCurrentUser(page-1, size);
    }


    @PreAuthorize("hasAuthority('Sale_staff') or (hasAuthority('CUSTOMER') and @feedBackService.isOwner(#feedBackId))")
    @DeleteMapping("/{feedBackId}")
    public ResponseEntity deleteFeedBack(@PathVariable long feedBackId) {
        feedBackService.deleteFeedBack(feedBackId);
        return ResponseEntity.ok().build();
    }
}