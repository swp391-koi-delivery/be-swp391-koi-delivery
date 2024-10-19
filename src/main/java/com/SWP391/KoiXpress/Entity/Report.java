package com.SWP391.KoiXpress.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String reportType;
    Date generateDate;
    String dateRange;

    @OneToMany(mappedBy = "report")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    List<Order> orders;
}
