package com.justdo.plug.blog.domain.category.repository;

import com.justdo.plug.blog.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
