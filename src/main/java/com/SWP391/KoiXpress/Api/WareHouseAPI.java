package com.SWP391.KoiXpress.Api;

import com.SWP391.KoiXpress.Entity.WareHouse;
import com.SWP391.KoiXpress.Service.WareHouseService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wareHouse")
@CrossOrigin("*")
@SecurityRequirement(name="api")
public class WareHouseAPI {

    @Autowired
    WareHouseService wareHouseService;

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody WareHouse wareHouse){
        WareHouse newWareHouse = wareHouseService.create(wareHouse);
        return ResponseEntity.ok(newWareHouse);
    }
}
