package com.SWP391.KoiXpress.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Entity
public class WareHouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long wareHouseId;
    String location;
    int stockCapacity;
    int currentStock;
    Boolean status;

    @OneToMany(mappedBy = "wareHouse")
    @JsonIgnore
    List<ProgressDetail> progressDetails;
}
