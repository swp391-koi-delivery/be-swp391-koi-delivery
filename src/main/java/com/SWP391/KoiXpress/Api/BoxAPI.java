package com.SWP391.KoiXpress.Api;

import com.SWP391.KoiXpress.Entity.Box;
import com.SWP391.KoiXpress.Model.request.BoxRequest;
import com.SWP391.KoiXpress.Service.BoxService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/box")
@CrossOrigin("*")
@SecurityRequirement(name = "api")
public class BoxAPI {

    @Autowired
    BoxService boxService;

    @PostMapping
    public ResponseEntity createBox(@Valid @RequestBody BoxRequest boxRequest){
        Box box = boxService.create(boxRequest);
        return ResponseEntity.ok(box);
    }
}
