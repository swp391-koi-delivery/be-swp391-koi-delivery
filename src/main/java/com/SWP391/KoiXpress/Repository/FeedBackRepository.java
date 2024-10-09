package com.SWP391.KoiXpress.Repository;

import com.SWP391.KoiXpress.Entity.FeedBack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedBackRepository extends JpaRepository<FeedBack, Long> {
FeedBack findByFeedBackId(long feedBackId);
}
