package com.SWP391.KoiXpress.Api;

import com.SWP391.KoiXpress.Model.response.Blog.AllBlogResponse;
import com.SWP391.KoiXpress.Service.BlogService;
import com.SWP391.KoiXpress.Service.BoxDetailService;
import com.SWP391.KoiXpress.Service.GeoCodingService;
import com.SWP391.KoiXpress.Service.RoutingService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/free-access")
@CrossOrigin("*")
@SecurityRequirement(name="api")
public class FreeAccessAPI {

    @Autowired
    BlogService blogService;

    @Autowired
    BoxDetailService boxDetailService;

    @Autowired
    RoutingService routingService;

    @Autowired
    GeoCodingService geoCodingService;

    @GetMapping("/allBlog")
    public ResponseEntity<List<AllBlogResponse>> getAllBlogs(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size){
        List<AllBlogResponse> blogResponses = blogService.getAllBlog(page - 1, size);
        return ResponseEntity.ok(blogResponses);
    }

    @GetMapping("/calculateBoxAndSuggestFishSizes")
    public ResponseEntity<Map<String, Object>> calculateBoxAndSuggestFishSizes(
            @RequestParam List<Integer> quantities,
            @RequestParam List<Double> fishSizes) {

        // Kiểm tra nếu số lượng fishSizes và quantities khớp nhau
        if (quantities.size() != fishSizes.size()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Số lượng và kích thước cá không khớp"));
        }

        // Tạo map chứa các cặp kích thước-số lượng
        Map<Double, Integer> fishSizeQuantityMap = new HashMap<>();
        for (int i = 0; i < fishSizes.size(); i++) {
            fishSizeQuantityMap.put(fishSizes.get(i), quantities.get(i));
        }

        // Gọi service để tính toán hộp và gợi ý kích thước cá có thể thêm
        Map<String, Object> result = boxDetailService.calculateBoxAndSuggestFishSizes(fishSizeQuantityMap);

        return ResponseEntity.ok(result);
    }


    @GetMapping("/route")
    public String route(@RequestParam String startLocation, @RequestParam String endLocation) {
        try {
            double[] startCoords = geoCodingService.geocoding(startLocation);
            double[] endCoords = geoCodingService.geocoding(endLocation);
            return routingService.getFormattedRoute(startCoords[0], startCoords[1], endCoords[0], endCoords[1]); // Use getFormattedRoute here
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

}
