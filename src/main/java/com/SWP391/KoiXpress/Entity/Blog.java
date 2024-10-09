package com.SWP391.KoiXpress.Entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long blogId;

    @Column(length=50)
    String img;

    @Column(length = 2000)
    String post;

    Boolean poststatus =false;

    @ManyToOne
    @JoinColumn(name="user_id")
    User user;
}
