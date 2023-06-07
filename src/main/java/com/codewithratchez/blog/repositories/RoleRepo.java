package com.codewithratchez.blog.repositories;

import com.codewithratchez.blog.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Integer> {

}
