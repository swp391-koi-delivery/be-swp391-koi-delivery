package com.SWP391.KoiXpress.Api;


import com.SWP391.KoiXpress.Model.request.RegisterRequestManager;
import com.SWP391.KoiXpress.Model.request.UpdateRequestManager;
import com.SWP391.KoiXpress.Model.response.LoginResponse;
import com.SWP391.KoiXpress.Model.response.RegisterResponse;
import com.SWP391.KoiXpress.Model.response.UpdateResponse;

import com.SWP391.KoiXpress.Service.ManagerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manager")
@CrossOrigin("*")
@SecurityRequirement(name="api")
public class ManagerAPI {

    @Autowired
    ManagerService managerService;

    @PostMapping
    public ResponseEntity createUser(@Valid @RequestBody RegisterRequestManager registerRequestManager) {
        RegisterResponse newUser = managerService.create(registerRequestManager);
        return ResponseEntity.ok(newUser);
    }
    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable long id, @Valid @RequestBody UpdateRequestManager updateRequestManager){
        UpdateResponse updateUser = managerService.update(id,updateRequestManager);
        return ResponseEntity.ok(updateUser);
    }

    @GetMapping
    public ResponseEntity<List<RegisterResponse>> get(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<RegisterResponse> registerResponses = managerService.getAllUser(page - 1, size);
        return ResponseEntity.ok(registerResponses);
    }

    @GetMapping("{id}")
    public ResponseEntity getEachUser(@PathVariable long id){
        RegisterResponse user = managerService.getEachUserById(id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable long id){
        LoginResponse deleteUser = managerService.delete(id);
        return ResponseEntity.ok(deleteUser);
    }
}
