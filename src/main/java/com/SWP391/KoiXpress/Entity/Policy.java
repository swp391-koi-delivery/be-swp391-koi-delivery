package com.SWP391.KoiXpress.Entity;

import com.SWP391.KoiXpress.Entity.Enum.PolicyStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
public class Policy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long policyId;
    @NotBlank(message = "Title can not be blank")
    String title;
    @NotBlank(message = "Post can not be blank")
    String post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @Enumerated(EnumType.STRING)
    PolicyStatus policyStatus;
}
