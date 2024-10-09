package com.SWP391.KoiXpress.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;


@Data
@Entity
public class Progress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long progressId;
    Date dateDelivery;
    Date dateRecived;
    String originLocation;
    String destinationLocation;
    Boolean status;

//    @ManyToOne
//    @JoinColumn(name="order_id")
//    Order order;

    @OneToMany(mappedBy = "progress")
    @JsonIgnore
    List<ProgressDetail> progressDetails;
}
