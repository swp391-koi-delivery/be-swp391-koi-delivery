package com.SWP391.KoiXpress.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
public class BoxDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    int quantity;

    @ManyToOne
    @JoinColumn(name = "order_detail_id")
    @JsonIgnore
    OrderDetail orderDetail;

    @ManyToOne
    @JoinColumn(name = "box_id")
    @JsonIgnore
    Box box;

}
