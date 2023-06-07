package com.codewithratchez.blog.repositories;

import com.codewithratchez.blog.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
}
