package com.SWP391.KoiXpress.Model.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FeedBackReplyResponse {
    private String replyContent;
    private String repliedBy;
    private LocalDateTime replyDate;
}
