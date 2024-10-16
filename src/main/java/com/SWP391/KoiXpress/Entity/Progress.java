package com.SWP391.KoiXpress.Entity;

import com.SWP391.KoiXpress.Entity.Enum.HealthFishStatus;
import com.SWP391.KoiXpress.Entity.Enum.ProgressStatus;
import com.SWP391.KoiXpress.Model.response.UserResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.NumberFormat;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Progress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String image;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    Date dateProgress;

    @NumberFormat(pattern = "#.##")
    double totalVolume;

    int totalBox;

    boolean isInProgress = false;

    @Enumerated(EnumType.STRING)
    HealthFishStatus healthFishStatus;

    @Enumerated(EnumType.STRING)
    ProgressStatus progressStatus;

//    @ManyToOne
//    @JoinColumn(name = "vehicle_id")
//    Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    WareHouse wareHouse;

    @ManyToOne
    @JoinColumn(name = "order_id")
    Order order;
}
