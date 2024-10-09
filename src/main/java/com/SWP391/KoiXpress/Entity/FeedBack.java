package com.SWP391.KoiXpress.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class FeedBack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long feedBackId;

    @JsonIgnore
    boolean isDeleted = false;

    @NotNull(message = "you need to rate")
    @Min(value = 0)
    @Max(value = 5)
    byte ratingScore;

    @Column(length = 200)
    String comment;

    LocalDateTime createdTime;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "feedBack")
    @JsonManagedReference
    List<FeedBackReply> replies;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
