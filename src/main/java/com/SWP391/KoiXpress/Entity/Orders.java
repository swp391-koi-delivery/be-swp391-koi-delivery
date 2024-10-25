package com.SWP391.KoiXpress.Entity;

import com.SWP391.KoiXpress.Entity.Enum.DescribeOrder;
import com.SWP391.KoiXpress.Entity.Enum.MethodTransPort;
import com.SWP391.KoiXpress.Entity.Enum.OrderStatus;
import com.SWP391.KoiXpress.Exception.OrderException;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.NumberFormat;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name="`order`")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    UUID trackingOrder = UUID.randomUUID();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    Date orderDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    Date deliveryDate;

    @NotBlank(message = "need information of recipient")
    String recipientInfo;

    @NotBlank(message = "location start can not be blank")
    String originLocation;

    String nearWareHouse;

    @NotBlank(message = "location end can not be blank")
    String destinationLocation;

    @Min(value = 0,message = "totalPrice at least 0")
    @NotNull(message = "totalPrice can not be null")
    double totalPrice;

    int totalQuantity;

    int totalBox;

    @NumberFormat(pattern = "#.##")
    double totalDistance;

    @NumberFormat(pattern = "#.##")
    double totalVolume;

    String customerNotes;

    @Enumerated(EnumType.STRING)
    DescribeOrder describeOrder;

    @Enumerated(EnumType.STRING)
    MethodTransPort methodTransPort;

    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name="user_id")
    Users users;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    List<OrderDetails> orderDetails;


    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    List<Progresses> progresses;

    @ManyToOne
    @JoinColumn(name = "report_id")
    Reports reports;

    @OneToMany(mappedBy = "orders",cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    List<FeedBacks> feedBacks;

    @OneToOne(mappedBy = "orders")
    Payments payments;


    public double calculatePrice(){
        if(methodTransPort!=null){
            if(describeOrder.equals(DescribeOrder.WHOLESALE_ORDER)){
                return totalDistance * methodTransPort.getPrice() + totalPrice-(totalPrice*describeOrder.getDiscount());
            }
            return totalDistance * methodTransPort.getPrice() + totalPrice;
        }
        throw new OrderException("Method transport is not selected");
    }

    public double calculateDistancePrice(){
        if(methodTransPort!= null){
            return totalDistance * methodTransPort.getPrice();
        }
        throw new OrderException("Method transport is not selected");
    }
}
