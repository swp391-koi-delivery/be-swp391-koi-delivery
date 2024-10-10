package com.SWP391.KoiXpress.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Min(value = 0, message = "price should greater than 0")
    double price;

    double VAT;

    public void setVAT(double VAT){
        this.VAT = 0.05;
    }
//    @OneToOne(mappedBy = "invoice")
//    Order order;
}
