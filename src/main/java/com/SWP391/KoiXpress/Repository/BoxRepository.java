package com.SWP391.KoiXpress.Repository;

import com.SWP391.KoiXpress.Entity.Box;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoxRepository extends JpaRepository<Box, Long> {
    Box findBoxByType(String boxType);
}
