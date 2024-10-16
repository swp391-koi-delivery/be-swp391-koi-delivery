package com.SWP391.KoiXpress.Entity;

import com.SWP391.KoiXpress.Entity.Enum.DescribeOrder;
import com.SWP391.KoiXpress.Entity.Enum.HealthFishStatus;
import com.SWP391.KoiXpress.Entity.Enum.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.NumberFormat;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    UUID healthCertificate = UUID.randomUUID();

    @NumberFormat(pattern = "#.##")
    double priceOfFish;

    @NotBlank(message = "nameFarm can not blank")
    String nameFarm;

    @NotBlank(message = "farmAddress can not blank")
    String farmAddress;

    @NotBlank(message = "origin of fish should not null")
    String origin;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    Date inspectionDate;

    @NotBlank(message = "fishSpecies can not blank")
    String fishSpecies;

    //
    @Min(value = 1)
    @Max(value = 100)
    int numberOfFish;
    @NumberFormat(pattern = "#.##")
    double sizeOfFish;
    //

    //
    int totalBox;
    @NumberFormat(pattern = "#.##")
    double totalVolume;
    @NumberFormat(pattern = "#.##")
    double price;
    //

    @Enumerated(EnumType.STRING)
    DescribeOrder describeOrder;

    @Enumerated(EnumType.STRING)
    HealthFishStatus healthFishStatus;

    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    Order order;

    @OneToMany(mappedBy = "orderDetail", cascade = CascadeType.ALL)
    List<BoxDetail> boxDetails;
}
