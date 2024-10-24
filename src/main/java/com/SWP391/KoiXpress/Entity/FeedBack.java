package com.SWP391.KoiXpress.Entity;


import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeedBack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @JsonIgnore
    boolean isDeleted = false;

    @NotNull(message = "you need to rate")
    @Min(value = 0)
    @Max(value = 5)
    byte ratingScore;

    @Column(length = 200)
    String comment;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    Date createdTime;

    boolean isDelete = false;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "feedBack")
    @JsonManagedReference
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    List<FeedBackReply> replies;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"feedBacks", "orders", "blogs"})
    User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    Order order;
}
