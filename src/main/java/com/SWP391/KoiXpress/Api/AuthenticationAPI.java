package com.SWP391.KoiXpress.Api;


import com.SWP391.KoiXpress.Exception.AuthException;
import com.SWP391.KoiXpress.Exception.NotFoundException;
import com.SWP391.KoiXpress.Model.request.ForgotPasswordRequest;
import com.SWP391.KoiXpress.Model.request.LoginRequest;
import com.SWP391.KoiXpress.Model.request.PasswordResetRequest;
import com.SWP391.KoiXpress.Model.request.RegisterRequest;
import com.SWP391.KoiXpress.Model.response.LoginResponse;
import com.SWP391.KoiXpress.Model.response.RegisterResponse;
import com.SWP391.KoiXpress.Service.AuthenticationService;
import com.SWP391.KoiXpress.Service.TokenService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
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

    @PostMapping("/google")
    public ResponseEntity<String> googleLogin(@RequestBody String idToken) {
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            String uid = decodedToken.getUid();
            // Add further logic (e.g., create user session, JWT)
            return ResponseEntity.ok("User verified: " + uid);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid token");
        }
    }

}
