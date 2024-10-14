package com.SWP391.KoiXpress.Model.response;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class FeedBackResponse {
    long Id;
    byte ratingScore;
    String comment;
    LocalDateTime createdTime;
    UserResponse userResponse;
    List<FeedBackReplyResponse> replies;
}
