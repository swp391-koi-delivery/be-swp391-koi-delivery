package com.SWP391.KoiXpress.Repository;

import com.SWP391.KoiXpress.Entity.FeedBack;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface FeedBackRepository extends JpaRepository<FeedBack, Long> {
    Page<FeedBack> findFeedBacksByUserId(Long userId, Pageable pageable);
    List<FeedBack> findFeedBacksByOrderId(Long orderId);

    FeedBack findById(long feedId);
}
