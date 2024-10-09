package com.SWP391.KoiXpress.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String type;
    int quantity;
    double size;
    double price;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "order_id")
    Order order;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "box_detail_id", referencedColumnName = "boxId")
    BoxDetail boxDetail;
}
