package com.soda.apiserver.auth.repository;

import com.soda.apiserver.auth.model.entity.User;
import com.soda.apiserver.auth.model.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    List<UserRole> findByUser(User user);
}
