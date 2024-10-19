package com.SWP391.KoiXpress.Repository;

import com.SWP391.KoiXpress.Entity.Order;
import com.SWP391.KoiXpress.Entity.WareHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WareHouseRepository extends JpaRepository<WareHouse, Long> {
    WareHouse findWareHouseById(long id);

    @Query("SELECT w.location FROM WareHouse w")
    List<String> findAllLocation();

    WareHouse findWareHouseByLocation(String nearWareHouse);


    List<WareHouse> findByIsAvailableTrue();

    List<WareHouse> findByIsAvailableFalse();
}
