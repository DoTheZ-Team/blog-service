package com.justdo.plug.blog.domain.blog.service;

import static com.justdo.plug.blog.domain.blog.dto.BlogResponse.BlogInfo;
import static com.justdo.plug.blog.domain.blog.dto.BlogResponse.MyBlogResult;
import static com.justdo.plug.blog.domain.blog.dto.BlogResponse.toBlogInfo;
import static com.justdo.plug.blog.domain.blog.dto.BlogResponse.toMyBlogResult;

import com.justdo.plug.blog.domain.blog.Blog;
import com.justdo.plug.blog.domain.blog.dto.BlogResponse;
import com.justdo.plug.blog.domain.blog.dto.BlogResponse.BlogItemList;
import com.justdo.plug.blog.domain.blog.repository.BlogRepository;
import com.justdo.plug.blog.domain.member.MemberClient;
import com.justdo.plug.blog.domain.member.MemberDTO;
import com.justdo.plug.blog.domain.subscription.service.SubscriptionQueryService;
import com.justdo.plug.blog.global.exception.ApiException;
import com.justdo.plug.blog.global.response.code.status.ErrorStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlogQueryService {

    private final BlogRepository blogRepository;
    private final MemberClient memberClient;
    private final SubscriptionQueryService subscriptionQueryService;

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

    // 구독 페이지에서 내가 구독하는 or 나를 구독하는 블로그의 정보
    public BlogItemList getBlogInfoList(Long memberId, int page) {

        // 내가 구독한 블로그의 아이디 목록
        List<Long> blogIdList = subscriptionQueryService.getSubscriptionBlogIdList(
            memberId, page);

        // 타블로그의 회원 목록
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("createdAt"));
        Slice<Blog> blogs = blogRepository.findBlogIdList(blogIdList, pageRequest);

        List<Long> memberIdList = blogs.stream()
            .map(Blog::getMemberId)
            .toList();
        List<String> memberNicknames = memberClient.findMemberNicknames(memberIdList);

        return BlogResponse.toBlogItemList(memberNicknames, blogs);

    }

}
