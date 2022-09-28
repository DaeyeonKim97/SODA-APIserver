package com.soda.apiserver.review.repository;

import com.soda.apiserver.review.model.entity.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findByName(String name);
    Category findById(int id);
    List<Category> findAllBy(Pageable pageable);
    int countBy();
}
