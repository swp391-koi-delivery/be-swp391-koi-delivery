package com.SWP391.KoiXpress.Api;

import com.SWP391.KoiXpress.Entity.Blog;
import com.SWP391.KoiXpress.Model.request.BlogRequest;
import com.SWP391.KoiXpress.Model.response.BlogResponse;
import com.SWP391.KoiXpress.Service.BlogService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blog")
@CrossOrigin("*")
@SecurityRequirement(name="api")
@PreAuthorize("hasAuthority('Customer')")
public class BlogAPI {

    @Autowired
    BlogService blogService;

    @PostMapping
    public ResponseEntity createBlog(@Valid @RequestBody BlogRequest blogRequest){
        BlogResponse newBlog = blogService.createBlog(blogRequest);
        return ResponseEntity.ok(newBlog);
    }

    @GetMapping
    public  ResponseEntity get(){
        List<BlogResponse> blogs = blogService.getAllBlog();
        return ResponseEntity.ok(blogs);
    }

    @DeleteMapping("{blogId}")
    public ResponseEntity deleteBlog(@PathVariable long blogId){
        Blog deleteBlog = blogService.delete(blogId);
        return ResponseEntity.ok(deleteBlog);
    }

    @PutMapping("{blogId}")
    public ResponseEntity updateBlog(@PathVariable long blogId,@Valid @RequestBody Blog blog){
        Blog newBlog = blogService.update(blogId,blog);
        return  ResponseEntity.ok(newBlog);
    }
}
