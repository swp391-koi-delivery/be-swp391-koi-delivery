package com.SWP391.KoiXpress.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class RoutingService {
    @Autowired
    RestTemplate restTemplate;




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


}
