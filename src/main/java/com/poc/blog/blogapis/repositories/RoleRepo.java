package com.poc.blog.blogapis.repositories;

import com.poc.blog.blogapis.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Integer> {
}
