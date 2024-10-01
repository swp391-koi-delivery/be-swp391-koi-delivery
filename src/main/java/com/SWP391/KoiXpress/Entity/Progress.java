package com.SWP391.KoiXpress.Entity;

import com.SWP391.KoiXpress.Entity.Enum.ProgressStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class Progress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long progressId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "orderId", referencedColumnName = "orderId")
//    private Order order; // Foreign key to Order
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "warehouseId", referencedColumnName = "warehouseId")
//    private Warehouse warehouse;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "deliveryId", referencedColumnName = "deliveryId")
//    private DeliveryMethod deliveryMethod;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "vehicleId", referencedColumnName = "vehicleId")
//    private Vehicle vehicle;

    @NotNull(message = "date can not be null")
    @Temporal(TemporalType.DATE)
    Date dateDelivery;

    @NotNull(message = "date can not be null")
    @Temporal(TemporalType.DATE)
    Date dateRecived;

    @NotBlank(message = "location start can not be blank")
    String originLocation;

    @NotBlank(message = "location end can not be blank")
    String destinationLocation;

    @Enumerated(EnumType.STRING)
    ProgressStatus progressStatus;

}
