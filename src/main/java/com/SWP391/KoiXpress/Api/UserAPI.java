package com.SWP391.KoiXpress.Api;


import com.SWP391.KoiXpress.Entity.Blog;
import com.SWP391.KoiXpress.Entity.FeedBack;
import com.SWP391.KoiXpress.Entity.User;
import com.SWP391.KoiXpress.Model.*;
import com.SWP391.KoiXpress.Service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
@SecurityRequirement(name="api")
public class UserAPI {
    /*
     * POST: Create
     * PUT: Update
     * GET: get
     * DELETE: remove
     * */
    @Autowired
    UserService userService;

    @PostMapping("/blog")
    public ResponseEntity createBlog(@Valid @RequestBody Blog blog){
        Blog newBlog = userService.generateBlog(blog);
        return ResponseEntity.ok(newBlog);
    }

    @PostMapping("/feedback")
    public ResponseEntity createFeedBack(@Valid @RequestBody FeedBack feedBack){
        FeedBack newFeedBack =userService.generateFeedBack(feedBack);
        return ResponseEntity.ok(newFeedBack);
    }

    @PostMapping
    public ResponseEntity createUser(@Valid @RequestBody RegisterRequest registerRequest){
        RegisterResponse newUser = userService.create(registerRequest);
        return ResponseEntity.ok(newUser);
    }
    @PutMapping("{userId}")
    public ResponseEntity update(@PathVariable long userId,@Valid @RequestBody UpdateRequest updateRequest){
        UpdateResponse updateUser = userService.update(userId,updateRequest);
        return ResponseEntity.ok(updateUser);
    }
    @GetMapping
    public ResponseEntity get(){
        List<RegisterResponse> users = userService.getCurrentUser();
        return ResponseEntity.ok(users);
    }
    @DeleteMapping("{userId}")
    public ResponseEntity delete(@PathVariable long userId){
        LoginResponse deleteUser = userService.delete(userId);
        return ResponseEntity.ok(deleteUser);
    }

}
