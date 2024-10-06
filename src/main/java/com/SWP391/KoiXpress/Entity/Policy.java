package com.SWP391.KoiXpress.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Policy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long policyId;
    String title;
    String post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
