package com.soda.apiserver.user.repository;

import com.soda.apiserver.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findById(int id);
    User findByUserName(String username);
    User findByEmail(String email);

    List<User> findByUserNameContaining(String userName);
}
