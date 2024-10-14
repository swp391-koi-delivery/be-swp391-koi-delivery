package com.SWP391.KoiXpress.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.NumberFormat;

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
    @NumberFormat(pattern = "#.##")
    double totalPrice;

    double TAX = 0.05;

    public double calculateTotalInvoice() {
        return totalPrice * (1 + TAX);
    }

    @OneToOne(mappedBy = "invoice")
    @JsonIgnore
    Order order;
}
