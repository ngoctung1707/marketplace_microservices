package com.marketplace.category.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marketplace.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

    boolean existsByName(String name);

    boolean existsBySlug(String slug);

    Optional<Category> findBySlug(String slug);

}
