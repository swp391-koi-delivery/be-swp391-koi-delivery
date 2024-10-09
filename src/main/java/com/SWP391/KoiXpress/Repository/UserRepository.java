package com.SWP391.KoiXpress.Repository;

import com.SWP391.KoiXpress.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByUserId(long userId);
    List<User> findUserByUserstatusFalse();
    User findUserByUsername(String username);
    User findUserByEmail(String email);
}
