package com.SWP391.KoiXpress.Api;


import com.SWP391.KoiXpress.Model.request.*;
import com.SWP391.KoiXpress.Model.response.LoginGoogleResponse;
import com.SWP391.KoiXpress.Model.response.LoginResponse;
import com.SWP391.KoiXpress.Model.response.RegisterResponse;
import com.SWP391.KoiXpress.Service.AuthenticationService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/forgot-password")
    public ResponseEntity forgotPassword(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest){
        authenticationService.forgotPassword(forgotPasswordRequest);
        return ResponseEntity.ok("Check your email!");
    }

    @PostMapping("/reset-password")
    public ResponseEntity resetPassword(@RequestBody PasswordResetRequest passwordResetRequest) {
            authenticationService.resetPassword(passwordResetRequest);
            return ResponseEntity.ok("Reset password successfully");
    }



    @PostMapping("/login-google")
    public ResponseEntity<?> googleLogin(@RequestBody LoginGoogleRequest loginGoogleRequest) {
        try {
            LoginGoogleResponse response = authenticationService.loginGoogle(loginGoogleRequest);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
}
