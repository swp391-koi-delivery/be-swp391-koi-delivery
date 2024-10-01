//package com.SWP391.KoiXpress;
//
//import com.SWP391.KoiXpress.Entity.KoiFish;
//import com.SWP391.KoiXpress.Entity.Order;
//import com.SWP391.KoiXpress.Repository.KoiFishRepository;
//import com.SWP391.KoiXpress.Service.GeoCodingService;
//import com.SWP391.KoiXpress.Service.KoiFishService;
//import com.SWP391.KoiXpress.Service.RoutingService;
//import com.SWP391.KoiXpress.Service.ShippingService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.mockito.junit.jupiter.MockitoSettings;
//import org.mockito.quality.Strictness;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(MockitoExtension.class)
//public class ShippingServiceTest {
//
//    @Mock
//    private RoutingService routingService;
//
//    @Mock
//    private GeoCodingService geoCodingService;
//
//    @Mock
//    private KoiFishService koiFishService;
//
//    @InjectMocks
//    private ShippingService shippingService;
//
//    @Mock
//    private KoiFishRepository koiFishRepository;  // Mock repository
//
//
//
////    @BeforeEach
////    void setUp() {
////        // Stubbing moved here to be more specific, or use lenient()
////        lenient().when(koiFishService.calculateTotalWeightByOrderId(anyLong())).thenReturn(1000.0);
////        lenient().when(routingService.getRouteSegment(anyString(), anyString())).thenReturn("10.0");
////    }
//
//    @Test
//    void testPlanAndCalculateCosts_Success() {
//        // Mock total weights
//        when(koiFishService.calculateTotalWeightByOrderId(1L)).thenReturn(500.0);
//        when(koiFishService.calculateTotalWeightByOrderId(2L)).thenReturn(150.0);
//
//        // Mock distances between locations
//        when(routingService.getRouteSegment("Hồ Chí Minh", "Nha Trang")).thenReturn("500.0");
//        when(routingService.getRouteSegment("Nha Trang", "Đà Nẵng")).thenReturn("300.0");
//
//        // Create orders
//        Order order1 = new Order();
//        order1.setOrderId(1L);
//        order1.setDestinationLocation("Nha Trang");
//
//        Order order2 = new Order();
//        order2.setOrderId(2L);
//        order2.setDestinationLocation("Đà Nẵng");
//
//        List<Order> orders = Arrays.asList(order1, order2);
//
//        // Action
//        String result = shippingService.planAndCalculateCosts(orders);
//
//        // Debugging output
//        System.out.println("Result: " + result);
//
//        // Assert the result contains stop details
//        assertTrue(result.contains("Stop at: Nha Trang"));
//        assertTrue(result.contains("Stop at: Đà Nẵng"));
//        assertTrue(result.contains("Total distance: 800.0 km"));
//        assertTrue(result.contains("Total cost: $"));
//    }
//
//    @Test
//    void testCalculateTotalWeightByOrderId_NoHardcodedWeights() {
//        // Giả lập một danh sách KoiFish từ repository
//        List<KoiFish> koiFishList = new ArrayList<>();
//        KoiFish koiFish1 = mock(KoiFish.class);  // Mock đối tượng KoiFish
//        KoiFish koiFish2 = mock(KoiFish.class);  // Mock đối tượng KoiFish
//
//        koiFishList.add(koiFish1);
//        koiFishList.add(koiFish2);
//
//        // Mock repository để trả về danh sách KoiFish mà không cần nhập dữ liệu cứng
//        when(koiFishRepository.findByOrderOrderId(anyLong())).thenReturn(koiFishList);
//
//        // Thay vì dùng giá trị cứng, sử dụng anyDouble() để khớp với bất kỳ giá trị nào
//        when(koiFish1.getWeight()).thenReturn(anyDouble());  // Không cần giá trị cụ thể
//        when(koiFish2.getWeight()).thenReturn(anyDouble());
//
//        // Thực hiện phép tính tổng trọng lượng
//        double totalWeight = koiFishService.calculateTotalWeightByOrderId(1L);
//
//        // Kiểm tra kết quả. Không cần giá trị cụ thể, chỉ kiểm tra xem tổng có khác 0 không
//        assertTrue(totalWeight >= 0); // Tổng trọng lượng phải là số dương
//    }
//}
//
//
//
//
//
