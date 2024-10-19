package com.SWP391.KoiXpress.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.NumberFormat;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Box {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String type;

    @NumberFormat(pattern = "#.##")
    double volume;

    @NumberFormat(pattern = "#.##")
    double price;

    @OneToMany(mappedBy = "box", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    List<BoxDetail> boxDetails;
}
