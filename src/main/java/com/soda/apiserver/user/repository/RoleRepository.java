package com.soda.apiserver.user.repository;

import com.soda.apiserver.user.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRoleName(String role);
}
