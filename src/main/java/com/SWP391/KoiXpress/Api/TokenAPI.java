package com.SWP391.KoiXpress.Api;

import com.SWP391.KoiXpress.Exception.AuthException;
import com.SWP391.KoiXpress.Service.TokenService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/token")
@CrossOrigin("*")
@SecurityRequirement(name="api")
public class TokenAPI {

    @Autowired
    TokenService tokenService;

    @PutMapping("/valid")
    public ResponseEntity validToken(String token){
        String email =  tokenService.getEmailByToken(token);
        if(email == null){
            throw new AuthException("Token not valid");
        }
        return ResponseEntity.ok(email);
    }
}
