//package com.SWP391.KoiXpress.Api;
//
//import com.SWP391.KoiXpress.Service.EmailService;
//import io.swagger.v3.oas.annotations.security.SecurityRequirement;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api")
//@CrossOrigin("*")
//@SecurityRequirement(name="api")
//public class EmailAPI {
//
//    @Autowired
//    EmailService emailService;
//
//    @GetMapping("/verify")
//    public ResponseEntity verifyEmail(@RequestParam("email") String email){
//        boolean isEmailValid = emailService.verifyEmail(email);
//        if (isEmailValid) {
//            return ResponseEntity.ok("Email is valid");
//        } else {
//            return ResponseEntity.badRequest().body("Invalid email address");
//        }
//    }
//}
