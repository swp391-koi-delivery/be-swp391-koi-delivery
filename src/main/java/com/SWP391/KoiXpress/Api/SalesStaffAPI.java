package com.SWP391.KoiXpress.Api;

import com.SWP391.KoiXpress.Entity.BoxDetail;
import com.SWP391.KoiXpress.Service.CalculateBoxService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
@SecurityRequirement(name="api")
public class SalesStaffAPI {
    @Autowired
    CalculateBoxService calculateBoxService;


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
        Map<String, Object> result = calculateBoxService.calculateBoxAndSuggestFishSizes(fishSizeQuantityMap);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/createBox")
    public ResponseEntity createBox(@RequestParam List<Integer> quantities,
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

        // Gọi service để tạo BoxDetail
        BoxDetail boxDetail = calculateBoxService.createBox(fishSizeQuantityMap);
        return ResponseEntity.ok(boxDetail);
    }
}
