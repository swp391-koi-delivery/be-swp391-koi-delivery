package com.SWP391.KoiXpress.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WareHouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String location;
    int stockCapacity;
    int currentStock;
    Boolean status;

    @OneToMany(mappedBy = "wareHouse")
    @JsonIgnore
    List<Progress> progresses;
}
