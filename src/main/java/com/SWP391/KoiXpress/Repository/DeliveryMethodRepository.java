package com.SWP391.KoiXpress.Repository;

import com.SWP391.KoiXpress.Entity.DeliveryMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryMethodRepository extends JpaRepository<DeliveryMethod, Long> {
    DeliveryMethod findDeliveryMethodById(long id);
}
