package com.SWP391.KoiXpress.Repository;

import com.SWP391.KoiXpress.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    Order findOrderByOrderId(long orderId);

}
