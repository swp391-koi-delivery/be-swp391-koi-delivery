package com.SWP391.KoiXpress.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.NumberFormat;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotBlank(message = "Need type of fish")
    String type;

    int quantity;

    @NumberFormat(pattern = "#.##")
    double size;

    double price;


    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "order_id")
    Order order;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "box_detail_id", referencedColumnName = "id")
    BoxDetail boxDetail;
}
