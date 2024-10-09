package com.SWP391.KoiXpress.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class FeedBack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long feedId;

    @JsonIgnore
    boolean isDeleted = false;

    @NotBlank(message = "you need to rate")
    @Min(value = 0)
    @Max(value = 5)
    byte ratingScore;

    @Column(length = 200)
    String comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
