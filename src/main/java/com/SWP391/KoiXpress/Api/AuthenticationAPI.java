package com.SWP391.KoiXpress.Api;


import com.SWP391.KoiXpress.Model.request.LoginRequest;
import com.SWP391.KoiXpress.Model.request.PasswordResetRequest;
import com.SWP391.KoiXpress.Model.request.RegisterRequest;
import com.SWP391.KoiXpress.Model.response.LoginResponse;
import com.SWP391.KoiXpress.Model.response.RegisterResponse;
import com.SWP391.KoiXpress.Service.AuthenticationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@CrossOrigin("*")
@SecurityRequirement(name="api")
public class AuthenticationAPI {

    @Autowired
    AuthenticationService authenticationService;


    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody RegisterRequest registerRequest){
        RegisterResponse newUser =  authenticationService.register(registerRequest);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody LoginRequest loginRequest){
        LoginResponse newUser = authenticationService.login(loginRequest);
        return ResponseEntity.ok(newUser);
    }

    public ResponseEntity forgotPassword(@RequestBody String email){
        authenticationService.forgotPassword(email);
        return ResponseEntity.ok("Check your email!");
    }

    public ResponseEntity resetPassword(PasswordResetRequest passwordResetRequest){
        authenticationService.resetPassword(passwordResetRequest);
        return ResponseEntity.ok("Reset password successfull");
    }
}
