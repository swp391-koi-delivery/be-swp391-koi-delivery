package com.SWP391.KoiXpress.Api;

import com.SWP391.KoiXpress.Entity.Order;
import com.SWP391.KoiXpress.Model.request.ProgressRequest;

import com.SWP391.KoiXpress.Model.response.ProgressResponse;
import com.SWP391.KoiXpress.Model.response.OrderResponseAll;
import com.SWP391.KoiXpress.Service.ProgressService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
@CrossOrigin("*")
@SecurityRequirement(name="api")
public class ProgressAPI {

    @Autowired
    ProgressService progressService;

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody ProgressRequest progressRequest){
        List<ProgressResponse> progresses = progressService.create(progressRequest);
        return ResponseEntity.ok(progresses);
    }
}