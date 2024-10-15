package com.SWP391.KoiXpress.Repository;

import com.SWP391.KoiXpress.Entity.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgressRepository extends JpaRepository<Progress, Long> {
    @Query("SELECT p FROM Progress p WHERE p.order.id = :orderId")
    List<Progress> findProgressesByOrderId(long orderId);
}
