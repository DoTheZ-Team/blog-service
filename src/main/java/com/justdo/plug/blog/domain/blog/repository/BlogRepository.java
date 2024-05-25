package com.justdo.plug.blog.domain.blog.repository;

import com.justdo.plug.blog.domain.blog.Blog;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BlogRepository extends JpaRepository<Blog, Long> {

    @Query("SELECT b FROM Blog b WHERE b.id in :blogIdList")
    List<Blog> findBlogIdList(List<Long> blogIdList);

    @Query("SELECT b FROM Blog b WHERE b.memberId in :memberIdList")
    List<Blog> findSubscriberIdList(List<Long> memberIdList);

    Blog findByMemberId(Long memberId);

    @Query("SELECT b FROM Blog b WHERE b.id IN :blogIdList")
    Page<Blog> findAllByBlogList(List<Long> blogIdList, PageRequest pageRequest);

    @Query("SELECT b FROM Blog b WHERE b.id IN :blogIdList")
    List<Blog> findAllByBlogs(List<Long> blogIdList);

    @Query("SELECT b FROM Blog b WHERE b.memberId IN :memberIdList")
    List<Blog> findAllByMemberIdList(List<Long> memberIdList);
}
