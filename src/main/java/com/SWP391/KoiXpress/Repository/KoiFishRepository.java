package com.SWP391.KoiXpress.Repository;

import com.SWP391.KoiXpress.Entity.KoiFish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KoiFishRepository extends JpaRepository<KoiFish, Long> {
    // Lấy danh sách KoiFish theo OrderId
     List<KoiFish> findByOrderOrderId(long orderId);

    // Lấy danh sách KoiFish theo BoxId
    List<KoiFish> findByBoxBoxId(long boxId);
}
