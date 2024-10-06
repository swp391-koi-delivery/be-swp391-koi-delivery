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
    @PutMapping("{userId}")
    public ResponseEntity update(@PathVariable long userId, @Valid @RequestBody UpdateRequestManager updateRequestManager){
        UpdateResponse updateUser = managerService.update(userId,updateRequestManager);
        return ResponseEntity.ok(updateUser);
    }

    @GetMapping
    public ResponseEntity get() {
        List<RegisterResponse> users = managerService.getAllUser();
        return ResponseEntity.ok(users);
    }
    @DeleteMapping("{userId}")
    public ResponseEntity delete(@PathVariable long userId){
        LoginResponse deleteUser = managerService.delete(userId);
        return ResponseEntity.ok(deleteUser);
    }
}
