package com.SWP391.KoiXpress.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;


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
    boolean isAvailable=true;

    @OneToMany(mappedBy = "wareHouse")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    List<Progress> progresses;
}
