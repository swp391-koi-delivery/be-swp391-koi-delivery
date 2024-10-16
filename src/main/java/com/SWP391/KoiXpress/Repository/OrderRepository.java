package com.SWP391.KoiXpress.Repository;

import com.SWP391.KoiXpress.Entity.Order;
import com.SWP391.KoiXpress.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    Order findOrderById(long Id);

    List<Order> findOrdersByUser(User user);

    List<Order> findOrdersByOriginLocationContainingIgnoreCase(String originLocation);

    List<Order> findOrdersByNearWareHouse(String nearWareHouse);

}
