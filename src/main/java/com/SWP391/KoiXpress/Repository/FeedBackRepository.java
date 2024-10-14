package com.SWP391.KoiXpress.Repository;

import com.SWP391.KoiXpress.Entity.FeedBack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedBackRepository extends JpaRepository<FeedBack, Long> {
    FeedBack findById(long feedId);
    List<FeedBack> findByUserId(Long userId);

}
