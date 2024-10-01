package com.SWP391.KoiXpress.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;

@Service
public class RoutingService {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    GeoCodingService geoCodingService;

    @Autowired
    ShippingService shippingService;


    private final String GRAPH_HOPPER_API_URL = "https://graphhopper.com/api/1/route";
    private final String API_KEY = "796003e4-061b-4a54-b815-3098412048a8";

    public String getRoute(double lat1, double lon1, double lat2, double lon2) {
        String url = GRAPH_HOPPER_API_URL + "?point=" + lat1 + "," + lon1 + "&point=" + lat2 + "," + lon2 + "&vehicle=car&key=" + API_KEY;

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody(); // Trả về JSON response
            } else if (response.getStatusCode()== HttpStatus.BAD_REQUEST) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST,"Location not found!");
            } else {
                throw new RuntimeException("Error calling GraphHopper API: " + response.getStatusCode());
            }
        } catch (Exception e) {
            // Xử lý ngoại lệ
            e.printStackTrace(); // In ngoại lệ ra log (có thể ghi vào log file nếu cần)
            return "Error: " + e.getMessage();
        }
    }
//    public String planRoute(Truck truck) {
//        StringBuilder routeDetails = new StringBuilder();
//        List<RouteStop> stops = truck.getStops();
//
//        for (int i = 0; i < stops.size() - 1; i++) {
//            RouteStop start = stops.get(i);
//            RouteStop end = stops.get(i + 1);
//            String segmentInfo = getRouteSegment(start.getLocation(), end.getLocation());
//            routeDetails.append(segmentInfo);
//            if (i < stops.size() - 2) {
//                routeDetails.append(" -> ");
//            }
//        }
//
//        return routeDetails.toString();
//    }
public String getRouteSegment(String startLocation, String endLocation) throws Exception {
    // Sử dụng GeoCodingService để lấy tọa độ cho startLocation và endLocation
    double[] startCoords = geoCodingService.geocoding(startLocation);
    double[] endCoords = geoCodingService.geocoding(endLocation);

    // Kiểm tra kết quả geocoding
    System.out.println("Start Coords: " + Arrays.toString(startCoords));
    System.out.println("End Coords: " + Arrays.toString(endCoords));

    String url = UriComponentsBuilder.fromHttpUrl(GRAPH_HOPPER_API_URL)
            .queryParam("point", startCoords[0] + "," + startCoords[1])
            .queryParam("point", endCoords[0] + "," + endCoords[1])
            .queryParam("vehicle", "truck")
            .queryParam("key", API_KEY)
            .toUriString();

    System.out.println("GraphHopper API URL: " + url); // In ra URL để kiểm tra

    try {
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // In ra toàn bộ phản hồi JSON từ API để kiểm tra
        System.out.println("API Response: " + response.getBody());

        return response.getBody(); // Trả về phản hồi JSON từ API
    } catch (Exception e) {
        e.printStackTrace();
        return "Error fetching route segment: " + e.getMessage();
    }
}
    public double calculateDistance(double[] coords1, double[] coords2) {
        String jsonResponse = getRoute(coords1[0], coords1[1], coords2[0], coords2[1]);
        return shippingService.parseDistanceFromRouteSegment(jsonResponse);
    }



    public double[] geocoding(String location) {
        // Simulate geocoding here, or ideally, call a real geocoding service.
        // For example, returning fixed coordinates for simplicity here.
        return new double[]{ /* latitude */, /* longitude */ };
    }


}
