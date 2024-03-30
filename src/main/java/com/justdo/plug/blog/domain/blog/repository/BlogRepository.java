package com.justdo.plug.blog.domain.blog.repository;

import com.justdo.plug.blog.domain.blog.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Long> {

}
