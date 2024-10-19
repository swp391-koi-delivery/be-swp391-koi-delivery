package com.SWP391.KoiXpress.Repository;

import com.SWP391.KoiXpress.Entity.Order;
import com.SWP391.KoiXpress.Entity.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProgressRepository extends JpaRepository<Progress, Long> {
    @Query("SELECT p FROM Progress p WHERE p.order.id = :orderId")
    List<Progress> findProgressesByOrderId(long orderId);

    @Query("SELECT p FROM Progress p WHERE p.order.trackingOrder = :trackingOrder AND p.progressStatus IS NOT NULL")
    List<Progress> findProgressesByOrderIdAndStatusNotNull(UUID trackingOrder);

    Progress findProgressesById(long id);

    Order findOrderByOrderId(Order order);
}
