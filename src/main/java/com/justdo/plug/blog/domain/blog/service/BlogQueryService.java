package com.justdo.plug.blog.domain.blog.service;

import static com.justdo.plug.blog.domain.blog.dto.BlogResponse.BlogInfo;
import static com.justdo.plug.blog.domain.blog.dto.BlogResponse.MyBlogResult;
import static com.justdo.plug.blog.domain.blog.dto.BlogResponse.toBlogInfo;
import static com.justdo.plug.blog.domain.blog.dto.BlogResponse.toMyBlogResult;

import com.justdo.plug.blog.domain.blog.Blog;
import com.justdo.plug.blog.domain.blog.repository.BlogRepository;
import com.justdo.plug.blog.domain.member.MemberClient;
import com.justdo.plug.blog.domain.member.MemberDTO;
import com.justdo.plug.blog.global.exception.ApiException;
import com.justdo.plug.blog.global.response.code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlogQueryService {

    private final BlogRepository blogRepository;
    private final MemberClient memberClient;

    public MyBlogResult getBlogInfo(Long blogId) {

        // 나의 블로그 정보 조회
        BlogInfo blogInfo = toBlogInfo(findById(blogId));

        // 나의 개인 정보 조회
        MemberDTO memberDTOInfo = memberClient.findMember();

        return toMyBlogResult(memberDTOInfo, blogInfo);
    }

    public Blog findById(Long blogId) {
        return blogRepository.findById(blogId).orElseThrow(
            () -> new ApiException(ErrorStatus._BLOG_NOT_FOUND)
        );
    }
}
