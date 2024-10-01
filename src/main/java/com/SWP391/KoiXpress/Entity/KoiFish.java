package com.SWP391.KoiXpress.Entity;

import com.SWP391.KoiXpress.Entity.Enum.FishHealthStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Entity

public class KoiFish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long koiFishId; // Primary key

    // Many KoiFish can be associated with one Order
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId", referencedColumnName = "orderId")
    private Order order; // Foreign key to Order

    // Many KoiFish can be associated with one Box
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boxId", referencedColumnName = "boxId")
    private Box box; // Foreign key to Box

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Size cannot be blank")
    private String size;

    @Min(value = 0, message = "Quantity must be at least 0")
    private int quantity;

    @Min(value = 0, message = "Weight must be at least 0")
    private double weight;

    @Min(value = 0, message = "Price must be at least 0")
    private double price;

    @Min(value=0, message = "Quantity at least 0")
    int requiredQuantity;


    @Enumerated(EnumType.STRING)
    private FishHealthStatus healthFishStatus;


}
