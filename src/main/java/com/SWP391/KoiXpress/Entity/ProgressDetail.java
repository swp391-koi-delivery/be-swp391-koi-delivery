package com.SWP391.KoiXpress.Entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProgressDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    Date dateDelivery;
    Date dateReceived;
    String originLocation;
    String destinationLocation;
    Boolean status;

    @ManyToOne
    @JoinColumn(name = "progress_id")
    Progress progress;

//    @ManyToOne
//    @JoinColumn(name = "vehicle_id")
//    Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    WareHouse wareHouse;

}
