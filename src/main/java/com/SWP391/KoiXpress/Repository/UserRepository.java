package com.SWP391.KoiXpress.Repository;

import com.SWP391.KoiXpress.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserById(long Id);
//    List<User> findUserByisDeletedFalse();
    User findUserByUsername(String username);
    User findUserByEmail(String email);

    @Query("SELECT u.email FROM User u WHERE u.Id = :Id")
    String findEmailById( long Id);
}
