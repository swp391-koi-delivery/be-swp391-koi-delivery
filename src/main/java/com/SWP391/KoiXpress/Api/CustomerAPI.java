package com.SWP391.KoiXpress.Api;

import com.SWP391.KoiXpress.Entity.Blog;
import com.SWP391.KoiXpress.Model.request.Blog.CreateBlogRequest;
import com.SWP391.KoiXpress.Model.request.Order.CreateOrderRequest;
import com.SWP391.KoiXpress.Model.request.User.UpdateCustomerRequest;
import com.SWP391.KoiXpress.Model.response.Blog.CreateBlogResponse;
import com.SWP391.KoiXpress.Model.response.Blog.DeleteBlogResponse;
import com.SWP391.KoiXpress.Model.response.Blog.UpdateBlogResponse;
import com.SWP391.KoiXpress.Model.response.Order.AllOrderByCurrentResponse;
import com.SWP391.KoiXpress.Model.response.Order.CreateOrderResponse;
import com.SWP391.KoiXpress.Model.response.User.DeleteUserByUserResponse;
import com.SWP391.KoiXpress.Model.response.User.UpdateCustomerResponse;
import com.SWP391.KoiXpress.Service.BlogService;
import com.SWP391.KoiXpress.Service.FeedBackService;
import com.SWP391.KoiXpress.Service.OrderService;
import com.SWP391.KoiXpress.Service.UserService;
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
@PreAuthorize("hasAuthority('CUSTOMER')")
public class CustomerAPI {
    @Autowired
    BlogService blogService;

    @Autowired
    UserService userService;

    @Autowired
    FeedBackService feedBackService;

    @Autowired
    OrderService orderService;

    @PostMapping("/blog")
    public ResponseEntity createBlog(@Valid @RequestBody CreateBlogRequest createBlogRequest){
        CreateBlogResponse newBlog = blogService.createBlog(createBlogRequest);
        return ResponseEntity.ok(newBlog);
    }

    @DeleteMapping("/blog/{blogId}")
    public ResponseEntity deleteBlog(@PathVariable long blogId){
        DeleteBlogResponse deleteBlog = blogService.delete(blogId);
        return ResponseEntity.ok(deleteBlog);
    }

    @PutMapping("/blog/{blogId}")
    public ResponseEntity updateBlog(@PathVariable long blogId,@Valid @RequestBody Blog blog){
        UpdateBlogResponse newBlog = blogService.update(blogId,blog);
        return  ResponseEntity.ok(newBlog);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateCustomer(@PathVariable long id,@Valid @RequestBody UpdateCustomerRequest updateCustomerRequest){
        UpdateCustomerResponse updateUser = userService.update(id, updateCustomerRequest);
        return ResponseEntity.ok(updateUser);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity deleteCustomer(@PathVariable long userId){
        DeleteUserByUserResponse deleteUserByUserResponse = userService.deleteByUser(userId);
        return ResponseEntity.ok(deleteUserByUserResponse);
    }

//    @PostMapping("/feedBack")
//    public ResponseEntity createFeedBack(@Valid @RequestBody FeedBackRequet feedBackRequet) {
//        FeedBack newFeedBack = feedBackService.createFeedBack(feedBackRequet);
//        return ResponseEntity.ok(newFeedBack);
//    }

    @PostMapping("/order")
    public ResponseEntity createOrder(@Valid @RequestBody CreateOrderRequest createOrderRequest) throws Exception {
        CreateOrderResponse order = orderService.create(createOrderRequest);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/each-user")
    public ResponseEntity getAllOrdersByCurrentUser(){
        List<AllOrderByCurrentResponse> createOrderResponseList = orderService.getAllOrdersByCurrentUser();
        return ResponseEntity.ok(createOrderResponseList);
    }

}
