package com.SWP391.KoiXpress.Api;


import com.SWP391.KoiXpress.Entity.User;
import com.SWP391.KoiXpress.Model.request.ForgotPasswordRequest;
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
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
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
    public ResponseEntity resetPassword(@RequestBody PasswordResetRequest passwordResetRequest){
        authenticationService.resetPassword(passwordResetRequest);
        return ResponseEntity.ok("Reset password successfully");
    }
    @GetMapping("/login/oauth2/success")
    public ResponseEntity<LoginResponse> handleGoogleLoginSuccess(OAuth2AuthenticationToken authentication) {
        OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
        String email = oidcUser.getEmail();

        User user = authenticationService.findUserByEmail(email);
        if (user == null) {
            user = authenticationService.registerOAuthUser(oidcUser);
        }

        LoginResponse loginResponse = authenticationService.createLoginResponse(user);
        return ResponseEntity.ok(loginResponse);
    }

}
