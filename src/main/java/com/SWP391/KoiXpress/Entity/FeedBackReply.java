package com.SWP391.KoiXpress.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
public class FeedBackReply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String replyContent;
    String repliedBy;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    Date replyDate;

    @ManyToOne
    @JoinColumn(name = "feed_back_id")
    @JsonBackReference
    FeedBack feedBack;


}
