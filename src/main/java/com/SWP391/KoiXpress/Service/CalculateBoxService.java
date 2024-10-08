package com.SWP391.KoiXpress.Service;

import com.SWP391.KoiXpress.Entity.BoxDetail;
import com.SWP391.KoiXpress.Exception.EntityNotFoundException;
import com.SWP391.KoiXpress.Model.request.BoxDetailRequest;
import com.SWP391.KoiXpress.Repository.BoxDetailRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CalculateBoxService {

    @Autowired
    BoxDetailRepository boxDetailRepository;

    @Autowired
    ModelMapper modelMapper;

    // Constants remain unchanged
    static final double SMALL_BOX_CAPACITY = 100;
    static final double MEDIUM_BOX_CAPACITY = 200;
    static final double LARGE_BOX_CAPACITY = 350;

    static final Map<String, Double> FISH_SIZES = new HashMap<>();

    static {
        FISH_SIZES.put("19.9", 10.0);
        FISH_SIZES.put("20-25", 15.0);
        FISH_SIZES.put("25.1-30", 20.0);
        FISH_SIZES.put("30.1-40", 30.0);
        FISH_SIZES.put("40.1-44", 40.0);
        FISH_SIZES.put("44.1-50", 50.0);
        FISH_SIZES.put("50.1-55", 60.0);
        FISH_SIZES.put("55.1-65", 70.0);
        FISH_SIZES.put("50-60", 75.0);
        FISH_SIZES.put("60.1-65", 80.0);
        FISH_SIZES.put("65.1-73", 90.0);
        FISH_SIZES.put("73.1-83", 100.0);
    }

    public double getFishVolume(int quantity, double size) {
        if (quantity <= 0) {
            throw new EntityNotFoundException("Kích thước hộp không hợp lệ");

        }
        if (size < 19.9 || size > 83) {
            throw new IllegalArgumentException("Kích thước cá Không hợp lệ (20-83).");
        }

        double total = 0;
        for (Map.Entry<String, Double> entry : FISH_SIZES.entrySet()) {
            String sizeRange = entry.getKey();
            double volume = entry.getValue();

            String[] bounds = sizeRange.split("-");
            if (bounds.length == 1) { // Single number
                if (Double.parseDouble(bounds[0]) == size) {
                    total = volume * quantity;
                }
            } else { // Range
                double lower = Double.parseDouble(bounds[0]);
                double upper = Double.parseDouble(bounds[1]);

                if (size >= lower && size <= upper) {
                    total = volume * quantity;
                }
            }
        }
        return total;
    }

    public Map<String, Object> calculateBox(Map<Double, Integer> fishSizeQuantityMap) {
        double usedVolume = 0;
        double totalVolume = 0;

        // Duyệt qua từng cặp kích thước-số lượng và cộng dồn thể tích tổng
        for (Map.Entry<Double, Integer> entry : fishSizeQuantityMap.entrySet()) {
            double fishSize = entry.getKey();
            int quantity = entry.getValue();
            totalVolume += getFishVolume(quantity, fishSize);
            usedVolume += getFishVolume(quantity, fishSize);
        }

        // Tính toán số lượng hộp dựa trên thể tích tổng
        double remainVolume = 0;
        int small_box_count = 0;
        int medium_box_count = 0;
        int large_box_count = 0;

        do {
            large_box_count = (int) (usedVolume / LARGE_BOX_CAPACITY);
            remainVolume = usedVolume - (large_box_count * LARGE_BOX_CAPACITY);
            if (remainVolume >= MEDIUM_BOX_CAPACITY) {
                medium_box_count = (int) (remainVolume / MEDIUM_BOX_CAPACITY);
                remainVolume -= (medium_box_count * MEDIUM_BOX_CAPACITY);
                if (remainVolume == 0) {
                    break;
                } else {
                    if (remainVolume <= SMALL_BOX_CAPACITY) {
                        small_box_count += 1;
                        remainVolume -= SMALL_BOX_CAPACITY;
                    } else {
                        medium_box_count += 1;
                        remainVolume -= MEDIUM_BOX_CAPACITY;
                    }
                }
            } else {
                if (remainVolume <= SMALL_BOX_CAPACITY) {
                    small_box_count += 1;
                    remainVolume -= SMALL_BOX_CAPACITY;
                } else {
                    medium_box_count += 1;
                    remainVolume -= MEDIUM_BOX_CAPACITY;
                }
            }
        } while (remainVolume > 0);

        // Chuẩn bị chi tiết hộp
        Map<String, Object> boxDetails = new LinkedHashMap<>();
        boxDetails.put("largeBoxCount", large_box_count);
        boxDetails.put("mediumBoxCount", medium_box_count);
        boxDetails.put("smallBoxCount", small_box_count);
        boxDetails.put("totalVolume", totalVolume);
        boxDetails.put("totalPrice", 0.0);
        boxDetails.put("remainingVolume", remainVolume = 0 - remainVolume);

        return boxDetails;
    }

    public List<String> suggestFishSizes(double remainVolume) {
        List<String> suggestions = new ArrayList<>();
        List<Map.Entry<String, Double>> sortedFishSizes = FISH_SIZES.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .toList();

        for (Map.Entry<String, Double> entry : sortedFishSizes) {
            double volume = entry.getValue();
            if (volume <= remainVolume) {
                int fishQuantity = (int) (remainVolume / volume);
                suggestions.add("Số lượng còn có thể thêm: " + fishQuantity + ", Size: " + entry.getKey());
            }
        }


        return suggestions;
    }

    public Map<String, Object> calculateBoxAndSuggestFishSizes(Map<Double, Integer> fishSizeQuantityMap) {
        Map<String, Object> boxDetails = calculateBox(fishSizeQuantityMap);
        double remainVolume = (double) boxDetails.get("remainingVolume");
        List<String> suggestions = suggestFishSizes(remainVolume);
        // Thêm gợi ý vào chi tiết hộp
        boxDetails.put("suggestions", suggestions);
        return boxDetails;
    }

    public BoxDetail createBox(Map<Double, Integer> fishSizeQuantityMap) {
        try {
            Map<String, Object> boxDetails = calculateBox(fishSizeQuantityMap);
            int largeBox = (int) boxDetails.get("largeBoxCount");
            int mediumBox = (int) boxDetails.get("mediumBoxCount");
            int smallBox = (int) boxDetails.get("smallBoxCount");
            double totalVolume = (double) boxDetails.get("totalVolume");

            double smallBoxPrice = smallBox * 5;  // Giá của Small Box: 5 USD
            double mediumBoxPrice = mediumBox * 10;  // Giá của Medium Box: 10 USD
            double largeBoxPrice = largeBox * 15;  // Giá của Large Box: 15 USD
            double totalPrice = smallBoxPrice + mediumBoxPrice + largeBoxPrice;

            BoxDetail boxDetail = new BoxDetail();
            boxDetail.setSmallBox(smallBox);
            boxDetail.setMediumBox(mediumBox);
            boxDetail.setLargeBox(largeBox);
            boxDetail.setTotalVolume(totalVolume);
            boxDetail.setTotalPrice(totalPrice);
            return boxDetailRepository.save(boxDetail);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Đã xảy ra lỗi trong quá trình tạo BoxDetail");
        }
    }


}
