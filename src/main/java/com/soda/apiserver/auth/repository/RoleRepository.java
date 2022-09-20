package com.soda.apiserver.auth.repository;

import com.soda.apiserver.auth.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRoleName(String role);
}
