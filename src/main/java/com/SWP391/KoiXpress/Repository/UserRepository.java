package com.SWP391.KoiXpress.Repository;

import com.SWP391.KoiXpress.Entity.User;
import com.SWP391.KoiXpress.Model.LoginResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByUserId(long userId);
    List<User> findUserByUserstatusFalse();
    User findUserByUsername(String username);
}
