package com.soda.apiserver.auth.repository;

import com.soda.apiserver.auth.model.entity.UserRole;
import com.soda.apiserver.auth.model.entity.embeded.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
}
