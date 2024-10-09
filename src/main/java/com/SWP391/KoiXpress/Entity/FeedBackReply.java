package com.SWP391.KoiXpress.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class FeedBackReply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String replyContent;
    String repliedBy;

    LocalDateTime replyDate;

    @ManyToOne
    @JoinColumn(name = "feed_back_id")
    @JsonBackReference
    FeedBack feedBack;


}
