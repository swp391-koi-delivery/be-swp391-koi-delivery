package com.SWP391.KoiXpress.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long invoiceId;
    double price;

    @OneToOne(mappedBy = "invoice")
    Order order;
}
