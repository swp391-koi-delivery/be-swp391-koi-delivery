package com.SWP391.KoiXpress.Api;


import com.SWP391.KoiXpress.Entity.Blog;
import com.SWP391.KoiXpress.Model.request.BlogRequest;
import com.SWP391.KoiXpress.Model.request.UpdateRequest;
import com.SWP391.KoiXpress.Model.response.LoginResponse;
import com.SWP391.KoiXpress.Model.response.UpdateResponse;
import com.SWP391.KoiXpress.Service.BlogService;
import com.SWP391.KoiXpress.Service.CustomerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin("*")
@SecurityRequirement(name="api")
//@PreAuthorize("hasAuthority('CUSTOMER')")
public class CustomerAPI {

    @Autowired
    CustomerService customerService;


    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable long id,@Valid @RequestBody UpdateRequest updateRequest){
        UpdateResponse updateUser = customerService.update(id,updateRequest);
        return ResponseEntity.ok(updateUser);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable long id){
        LoginResponse deleteUser = customerService.delete(id);
        return ResponseEntity.ok(deleteUser);
    }

}
