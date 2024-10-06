package com.SWP391.KoiXpress.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class ProgressDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long progressDetailId;
    Date dateDelivery;
    Date dateReceived;
    String originLocation;
    String destinationLocation;
    Boolean status;

    @ManyToOne
    @JoinColumn(name = "progress_id")
    Progress progress;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    WareHouse wareHouse;

}
