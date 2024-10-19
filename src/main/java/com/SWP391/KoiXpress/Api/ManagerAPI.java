package com.SWP391.KoiXpress.Api;

import com.SWP391.KoiXpress.Model.request.Box.CreateBoxRequest;
import com.SWP391.KoiXpress.Model.request.User.CreateUserByManagerRequest;
import com.SWP391.KoiXpress.Model.request.User.UpdateUserByManagerRequest;
import com.SWP391.KoiXpress.Model.request.WareHouse.CreateWareHouseRequest;
import com.SWP391.KoiXpress.Model.response.Box.AllBoxDetailResponse;
import com.SWP391.KoiXpress.Model.response.Box.CreateBoxResponse;
import com.SWP391.KoiXpress.Model.response.CreateWarehouseResponse;
import com.SWP391.KoiXpress.Model.response.User.*;
import com.SWP391.KoiXpress.Service.BoxDetailService;
import com.SWP391.KoiXpress.Service.BoxService;
import com.SWP391.KoiXpress.Service.UserService;
import com.SWP391.KoiXpress.Service.WareHouseService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manager")
@CrossOrigin("*")
@SecurityRequirement(name="api")
@PreAuthorize("hasAuthority('MANAGER')")
public class ManagerAPI {

    @Autowired
    BoxService boxService;

    @Autowired
    BoxDetailService boxDetailService;

    @Autowired
    UserService userService;

    @Autowired
    WareHouseService wareHouseService;

    @PostMapping("/box")
    public ResponseEntity createBox(@Valid @RequestBody CreateBoxRequest createBoxRequest){
        CreateBoxResponse box = boxService.create(createBoxRequest);
        return ResponseEntity.ok(box);
    }

    @GetMapping("/allBoxDetail")
    public ResponseEntity getAll() {
        List<AllBoxDetailResponse> boxDetails = boxDetailService.getAllBox();
        return ResponseEntity.ok(boxDetails);
    }

    @PostMapping("/user")
    public ResponseEntity createUserByManager(@Valid @RequestBody CreateUserByManagerRequest createUserByManagerRequest) {
        CreateUserByManagerResponse newUser = userService.create(createUserByManagerRequest);
        return ResponseEntity.ok(newUser);
    }

    @PutMapping("/{userId}")
    public ResponseEntity updateUserByManager(@PathVariable long userId, @Valid @RequestBody UpdateUserByManagerRequest updateUserByManagerRequest){
        UpdateCustomerResponse updateUser = userService.update(userId, updateUserByManagerRequest);
        return ResponseEntity.ok(updateUser);
    }

    @GetMapping("/allUser")
    public ResponseEntity<List<RegisterResponse>> getAllUser(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<RegisterResponse> registerResponses = managerService.getAllUser(page - 1, size);
        return ResponseEntity.ok(registerResponses);
    }

    @GetMapping("/{userId}")
    public ResponseEntity getEachUser(@PathVariable long userId){
        EachUserResponse user = userService.getEachUserById(userId);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUserByManager(@PathVariable long userId){
        DeleteUserByManagerResponse deleteUser = userService.deleteByManager(userId);
        return ResponseEntity.ok(deleteUser);
    }

    @PostMapping("/wareHouse")
    public ResponseEntity create(@Valid @RequestBody CreateWareHouseRequest wareHouse){
        CreateWarehouseResponse newWareHouse = wareHouseService.create(wareHouse);
        return ResponseEntity.ok(newWareHouse);
    }

    @DeleteMapping("/wareHouse/{id}")
    public ResponseEntity delete(@PathVariable long id){
        wareHouseService.delete(id);
        return ResponseEntity.ok("Delete success");
    }

    @GetMapping("/wareHouse/available")
    public ResponseEntity getAllWareHouseAvailable(){
        return ResponseEntity.ok(wareHouseService.getAllWareHouseAvailable());
    }

    @GetMapping("/wareHouse/notAvailable")
    public ResponseEntity getAllWareHouseNotAvailable(){
        return ResponseEntity.ok(wareHouseService.getAllWareHouseNotAvailable());
    }

}
