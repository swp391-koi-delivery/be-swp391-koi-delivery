package com.SWP391.KoiXpress.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long reportId;
    String reportType;
    Date generateDate;
    String dateRange;

    @OneToMany(mappedBy = "report")
    @JsonIgnore
    List<Order> orders;
}
