package com.SWP391.KoiXpress.Service;

import com.SWP391.KoiXpress.Entity.Box;
import com.SWP391.KoiXpress.Entity.BoxDetail;
import com.SWP391.KoiXpress.Model.response.Box.AllBoxDetailResponse;
import com.SWP391.KoiXpress.Model.response.Box.CreateBoxDetailResponse;
import com.SWP391.KoiXpress.Exception.BoxException;
import com.SWP391.KoiXpress.Repository.BoxDetailRepository;
import com.SWP391.KoiXpress.Repository.BoxRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BoxDetailService {

    @Autowired
    BoxDetailRepository boxDetailRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    BoxRepository boxRepository;


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
        FISH_SIZES.put("65.1-70", 75.0);
        FISH_SIZES.put("70.1-75", 80.0);
        FISH_SIZES.put("75.1-80", 90.0);
        FISH_SIZES.put("80.1-83", 100.0);
    }

    public double getFishVolume(int quantity, double size) {
        if (quantity <= 0) {
            throw new BoxException("Invalid box ");

        }
        if (size < 19.9 || size > 83) {
            throw new BoxException("Invalid fish size (20-83).");
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
        double remainVolume = 0;
        double totalVolume = 0;
        double totalPrice = 0;
        double remainingSpaceInSmallBox = 0;
        int totalCount = 0;

        // Duyệt qua từng cặp kích thước-số lượng và cộng dồn thể tích tổng
        for (Map.Entry<Double, Integer> entry : fishSizeQuantityMap.entrySet()) {
            double fishSize = entry.getKey();
            int quantity = entry.getValue();
            totalVolume += getFishVolume(quantity, fishSize);
            remainVolume += getFishVolume(quantity, fishSize);
        }

        List<Box> boxes = boxRepository.findAll(Sort.by(Sort.Order.desc("volume")));
        Box smallestBox = boxes.get(boxes.size()-1);

        Map<String, Integer> boxCount = new LinkedHashMap<>();

        for(Box box : boxes){
            boxCount.put(box.getType(),0);
        }

        for(Box box : boxes){
            int count = (int) (remainVolume / box.getVolume());
            remainVolume -= count * box.getVolume();
            boxCount.put(box.getType(), boxCount.get(box.getType())+ count);
            if (remainVolume == 0) {
                break;
            }
        }
        if(remainVolume > 0 && remainVolume < smallestBox.getVolume()){
            boxCount.put(smallestBox.getType(), boxCount.get(smallestBox.getType()) + 1);
            remainingSpaceInSmallBox = smallestBox.getVolume() - remainVolume;
        }

        for(Box box : boxes){
            totalPrice = box.getPrice() * boxCount.get(box.getType());
            totalPrice += totalPrice;
            totalCount += boxCount.get(box.getType());
        }

        // Chuẩn bị chi tiết hộp
        Map<String, Object> boxDetails = new LinkedHashMap<>();

        for(Map.Entry<String, Integer> entry : boxCount.entrySet()){
            String boxType = entry.getKey();
            int quantityBox = entry.getValue();
            boxDetails.put(boxType,quantityBox);
        }
        boxDetails.put("totalCount", totalCount);
        boxDetails.put("totalVolume", totalVolume);
        boxDetails.put("totalPrice",totalPrice);
        boxDetails.put("remainingVolume", remainingSpaceInSmallBox);

        return boxDetails;
    }

    public List<String> suggestFishSizes(double remainVolume){
        List<String> suggestions = new ArrayList<>();
        List<Map.Entry<String, Double>> sortedFishSizes = FISH_SIZES.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .toList();

        for (Map.Entry<String, Double> entry : sortedFishSizes) {
            double volume = entry.getValue();
            if (volume <= remainVolume) {
                int fishQuantity = (int) (remainVolume / volume);
                suggestions.add("Quantity can be added: " + fishQuantity + ", Size: "+ entry.getKey());
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

    public CreateBoxDetailResponse createBox(Map<Double, Integer> fishSizeQuantityMap){
        try{
            Map<String, Object> boxDetails = calculateBox(fishSizeQuantityMap);
            List<BoxDetail> boxDetailList = new ArrayList<>();
            double totalVolume = (double) boxDetails.get("totalVolume");
            double totalPrice = (double) boxDetails.get("totalPrice");
            int totalCount = (int) boxDetails.get("totalCount");
            for(Map.Entry<String, Object> entry : boxDetails.entrySet()){
                String boxType = entry.getKey();
                Object value = entry.getValue();
                if (boxType.equals("totalVolume") || boxType.equals("totalPrice") || boxType.equals("remainingVolume") || boxType.equals("totalCount")) {
                    continue;
                }
                Integer quantityBox = (Integer) value;
                Box box = boxRepository.findBoxByType(boxType);
                if(box != null){
                    BoxDetail boxDetail = new BoxDetail();
                    boxDetail.setBox(box);
                    boxDetail.setQuantity(quantityBox);
                    boxDetailRepository.save(boxDetail);
                    boxDetailList.add(boxDetail);
                }
            }
            CreateBoxDetailResponse createBoxDetailResponse = new CreateBoxDetailResponse();
            createBoxDetailResponse.setBoxDetails(boxDetailList);
            createBoxDetailResponse.setTotalPrice(totalPrice);
            createBoxDetailResponse.setTotalVolume(totalVolume);
            createBoxDetailResponse.setTotalCount(totalCount);

            return createBoxDetailResponse;
        }catch(Exception e){
            e.printStackTrace();
            throw new BoxException("BoxDetail cant create");
        }
    }

    public List<AllBoxDetailResponse> getAllBox(){
        List<BoxDetail> boxDetails = boxDetailRepository.findAll();
        if(boxDetails.isEmpty()){
            return Collections.emptyList();
        }

        return boxDetails.stream()
                .map(boxDetail -> new AllBoxDetailResponse(
                        boxDetail.getId(),
                        boxDetail.getQuantity(),
                        boxDetail.getOrderDetail(),
                        boxDetail.getBox()
                ))
                .collect(Collectors.toList());
    }


}
