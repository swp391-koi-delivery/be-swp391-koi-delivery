package com.SWP391.KoiXpress.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculateBoxService {
    // Tính toán thể tích cá dựa trên size

    // Khai báo các dung tích của hộp và các khoảng size cá
    static final double SMALL_BOX_CAPACITY = 100;  // Thể tích hộp trung bình
    static final double MEDIUM_BOX_CAPACITY = 200; // Thể tích hộp to
    static final double LARGE_BOX_CAPACITY = 350;  // Thể tích hộp siêu to

    // Bảng chứa size cá và thể tích chiếm dụng
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

    public static double getFishVolume(int quantity, double size) {
        double total = 0;
        for (Map.Entry<String, Double> entry : FISH_SIZES.entrySet()) {
            String sizeRange = entry.getKey();
            double volume = entry.getValue();

            String[] bounds = sizeRange.split("-");
            if (bounds.length == 1) { // Một số duy nhất
                if (Double.parseDouble(bounds[0]) == size) {
                    total = volume * quantity;
                    System.out.println("thể tích tổng thể: " + total);
                    break;

                }
            } else { // Khoảng giá trị
                double lower = Double.parseDouble(bounds[0]);
                double upper = Double.parseDouble(bounds[1]);

                if (size >= lower && size <= upper) {
                    total = volume * quantity;
                    System.out.println("thể tích tổng thể: " + total);
                    break;
                }
            }
        }
        return total;
    }

    public static double findSuitableBox(int quantity, double fishSize) {
        double usedVolume = getFishVolume(quantity, fishSize);
        double remainVolume = 0;
        int small_box_count = 0;
        int medium_box_count = 0;
        int large_box_count = 0;

        do {
            // Xử lý phần dư của hộp lớn
            large_box_count = (int) (usedVolume / LARGE_BOX_CAPACITY);
            remainVolume = usedVolume - (large_box_count * LARGE_BOX_CAPACITY);

            // Xử lý phần dư của hộp vừa
            if (remainVolume > 0) {
                medium_box_count = (int) (remainVolume / MEDIUM_BOX_CAPACITY);
                remainVolume = remainVolume - (medium_box_count * MEDIUM_BOX_CAPACITY);

            }
            // Xử lý phần dư của hộp nhỏ
            if (remainVolume > 0 && remainVolume <= SMALL_BOX_CAPACITY) {
                small_box_count += 1;
                break; // Thoát vòng lặp vì không còn thể tích để xử lý
            }

        } while (remainVolume > 0);

        System.out.println("total Large_Box need: "+large_box_count+"\n"+
                "total Medium_Box neeed: "+medium_box_count+"\n"+
                "total Small_Box need: "+small_box_count+"\n");
        return  remainVolume=(double)(SMALL_BOX_CAPACITY-remainVolume);
    }

    // Đề xuất các size còn lại có thể bỏ vào hộp
    public static void suggestFishSizes(int quantity, double fishSize) {
        double remainVolume = findSuitableBox(quantity, fishSize);
        int quantityBoxExtra = 0;
        System.out.println("Suggest:");

        // Sắp xếp các size cá theo thứ tự tăng dần dựa trên thể tích
        List<Map.Entry<String, Double>> sortedFishSizes = FISH_SIZES.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue()) // Sắp xếp theo giá trị (thể tích)
                .toList();

        for (Map.Entry<String, Double> entry : sortedFishSizes) {
            double volume = entry.getValue();
            if (volume <= remainVolume) {
                int SL = (int) (remainVolume / volume);

                System.out.println("SLuong Còn add được: " + SL + " Size: " + entry.getKey() + " (Thể tích: " + volume + ")");
            }
        }
    }
}
