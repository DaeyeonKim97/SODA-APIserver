package com.soda.apiserver.user.repository;

import com.soda.apiserver.user.model.entity.LoginLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginLogRepository extends JpaRepository<LoginLog, Integer> {
}
