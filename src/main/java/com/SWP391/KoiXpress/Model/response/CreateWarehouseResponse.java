package com.SWP391.KoiXpress.Model.response;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class CreateWarehouseResponse {
    long id;
    String location;
    boolean isAvailable;
}
