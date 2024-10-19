package com.SWP391.KoiXpress.Api;

import com.SWP391.KoiXpress.Service.FeedBackService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedBack")
@CrossOrigin("*")
@SecurityRequirement(name = "api")
@PreAuthorize("hasAuthority('SALE_STAFF')")
public class SaleAPI {

    @Autowired
    FeedBackService feedBackService;

//    @PostMapping("/{feedBackId}/reply")
//    public ResponseEntity replyToFeedBack(@PathVariable long feedBackId,
//                                          @RequestBody String replyContent,
//                                          Principal principal) {
//        String repliedBy = principal.getName();
//        FeedBackReply reply = feedBackService.replyToFeedBack(feedBackId, replyContent, repliedBy);
//        return ResponseEntity.ok(reply);
//    }
//
//    @GetMapping("/user/{userId}/feedbacks")
//    public ResponseEntity getFeedbacksByUser(@PathVariable Long userId) {
//        List<FeedBackResponse> feedBacks = feedBackService.getAllFeedBacksByUser(userId);
//        return ResponseEntity.ok(feedBacks);
//    }


}
