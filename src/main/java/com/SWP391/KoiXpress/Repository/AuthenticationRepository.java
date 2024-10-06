package com.SWP391.KoiXpress.Repository;

import com.SWP391.KoiXpress.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthenticationRepository extends JpaRepository<User, Long> {
    User findUserByUserId(long userId);
}
