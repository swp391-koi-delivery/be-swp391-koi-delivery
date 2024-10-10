package com.SWP391.KoiXpress.Repository;

import com.SWP391.KoiXpress.Entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    Blog findBlogById(long Id);
}
