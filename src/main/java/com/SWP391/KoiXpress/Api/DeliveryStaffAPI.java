package com.SWP391.KoiXpress.Api;

import com.SWP391.KoiXpress.Entity.Progress;
import com.SWP391.KoiXpress.Model.request.Progress.ProgressRequest;
import com.SWP391.KoiXpress.Model.request.Progress.UpdateProgressRequest;
import com.SWP391.KoiXpress.Model.response.Progress.DeleteProgressResponse;
import com.SWP391.KoiXpress.Model.response.Progress.ProgressResponse;
import com.SWP391.KoiXpress.Model.response.Progress.UpdateProgressResponse;
import com.SWP391.KoiXpress.Service.ProgressService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/delivery")
@CrossOrigin("*")
@SecurityRequirement(name="api")
@PreAuthorize("hasAuthority('DELIVERING_STAFF')")
public class DeliveryStaffAPI {

    @Autowired
    ProgressService progressService;

    @PostMapping("/progress")
    public ResponseEntity create(@Valid @RequestBody ProgressRequest progressRequest){
        List<ProgressResponse> progresses = progressService.create(progressRequest);
        return ResponseEntity.ok(progresses);
    }

    @GetMapping("/trackingOrder")
    public ResponseEntity trackingOrder(UUID trackingOrder){
        List<ProgressResponse> progresses = progressService.trackingOrder(trackingOrder);
        return ResponseEntity.ok(progresses);
    }

    @PostMapping("{id}")
    public ResponseEntity update(@PathVariable long id,@Valid @RequestBody UpdateProgressRequest updateProgressRequest){
        UpdateProgressResponse updateProgressResponse = progressService.update(id, updateProgressRequest);
        return ResponseEntity.ok(updateProgressResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable long id){
        DeleteProgressResponse deleteProgressResponse = progressService.delete(id);
        return ResponseEntity.ok(deleteProgressResponse);
    }

}
