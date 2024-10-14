package com.SWP391.KoiXpress.Repository;

import com.SWP391.KoiXpress.Entity.WareHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WareHouseRepository extends JpaRepository<WareHouse, Long> {
    WareHouse findWareHouseById(long id);
}
