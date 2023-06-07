package com.codewithratchez.blog.repositories;

import com.codewithratchez.blog.entities.Category;
import com.codewithratchez.blog.entities.Post;
import com.codewithratchez.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Integer> {
    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);
    List<Post> findByTitleContaining(String title);
}
