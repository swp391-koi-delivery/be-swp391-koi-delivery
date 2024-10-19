package com.SWP391.KoiXpress.Model.response.FeedBack;

import com.SWP391.KoiXpress.Model.response.User.UserResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FeedBackResponse {
    long Id;
    byte ratingScore;
    String comment;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    Date createdTime;
    UserResponse userResponse;
    List<FeedBackReplyResponse> replies;
}
