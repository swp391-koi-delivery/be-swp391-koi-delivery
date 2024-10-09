package com.SWP391.KoiXpress.Repository;

import com.SWP391.KoiXpress.Entity.BoxDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoxDetailRepository extends JpaRepository<BoxDetail,Long> {

}
