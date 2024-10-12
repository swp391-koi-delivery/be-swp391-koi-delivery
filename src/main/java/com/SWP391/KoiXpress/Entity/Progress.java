package com.SWP391.KoiXpress.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Progress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    Date dateDelivery;
    Date dateReceived;
    String originLocation;
    String destinationLocation;
    boolean status;

    @OneToMany(mappedBy = "progress")
    @JsonIgnore
    List<ProgressDetail> progressDetails;
}
