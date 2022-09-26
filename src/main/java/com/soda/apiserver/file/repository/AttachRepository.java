package com.soda.apiserver.file.repository;

import com.soda.apiserver.file.model.entity.Attach;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachRepository extends JpaRepository<Attach, Integer> {
}
