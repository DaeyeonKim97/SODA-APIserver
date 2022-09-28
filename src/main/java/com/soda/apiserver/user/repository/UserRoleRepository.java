package com.soda.apiserver.user.repository;

import com.soda.apiserver.user.model.entity.User;
import com.soda.apiserver.user.model.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    List<UserRole> findByUser(User user);
}
