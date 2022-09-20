package com.soda.apiserver.auth.repository;

import com.soda.apiserver.auth.model.entity.LoginLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginLogRepository extends JpaRepository<LoginLog, Integer> {
}
