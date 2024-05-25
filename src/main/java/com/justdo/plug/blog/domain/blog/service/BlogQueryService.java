package com.justdo.plug.blog.domain.blog.service;

import static com.justdo.plug.blog.domain.blog.dto.BlogResponse.BlogInfo;
import static com.justdo.plug.blog.domain.blog.dto.BlogResponse.MyBlogResult;
import static com.justdo.plug.blog.domain.blog.dto.BlogResponse.toBlogInfo;
import static com.justdo.plug.blog.domain.blog.dto.BlogResponse.toBlogpage;
import static com.justdo.plug.blog.domain.blog.dto.BlogResponse.toMyBlogResult;

import com.justdo.plug.blog.domain.blog.Blog;
import com.justdo.plug.blog.domain.blog.dto.BlogResponse;
import com.justdo.plug.blog.domain.blog.dto.BlogResponse.BlogInfoList;
import com.justdo.plug.blog.domain.blog.dto.BlogResponse.BlogItem;
import com.justdo.plug.blog.domain.blog.dto.BlogResponse.BlogItemList;
import com.justdo.plug.blog.domain.blog.dto.BlogResponse.BlogPage;
import com.justdo.plug.blog.domain.blog.dto.BlogResponse.BlogRecommend;
import com.justdo.plug.blog.domain.blog.dto.BlogResponse.CommentBlog;
import com.justdo.plug.blog.domain.blog.repository.BlogRepository;
import com.justdo.plug.blog.domain.member.MemberClient;
import com.justdo.plug.blog.domain.member.MemberDTO;
import com.justdo.plug.blog.domain.post.PostClient;
import com.justdo.plug.blog.domain.post.PostResponse.BlogPostItem;
import com.justdo.plug.blog.domain.recommendation.RecommendationClient;
import com.justdo.plug.blog.domain.recommendation.RecommendationDTO;
import com.justdo.plug.blog.domain.recommendation.RecommendationDTO.RecommendationRequest;
import com.justdo.plug.blog.domain.subscription.Subscription;
import com.justdo.plug.blog.domain.subscription.service.SubscriptionQueryService;
import com.justdo.plug.blog.global.exception.ApiException;
import com.justdo.plug.blog.global.response.code.status.ErrorStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    private final SubscriptionQueryService subscriptionQueryService;
    private final MemberClient memberClient;
    private final PostClient postClient;
    private final RecommendationClient recommendationClient;

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

    /**
     * 구독 페이지에서 내가 구독하는 블로그의 정보
     **/
    public BlogItemList getBlogInfoList(Long memberId, int page) {

        // 내가 구독한 블로그의 아이디 목록
        Slice<Subscription> subscriptionList = subscriptionQueryService.getMySubscriptionsSlice(
                memberId, page);
        List<Long> blogIdList = subscriptionList.stream()
                .map(Subscription::getToBlogId)
                .toList();

        // 내가 구독한 블로그 조회
        List<Blog> blogs = blogRepository.findBlogIdList(blogIdList);

        // 타블로그의 회원 ID 목록 조회
        List<Long> memberIdList = blogs.stream()
                .map(Blog::getMemberId)
                .toList();
        List<String> memberNicknames = memberClient.findMemberNicknames(memberIdList);

        return BlogResponse.toBlogItemList(memberNicknames, blogs, subscriptionList);

    }

    /**
     * 구독 페이지 - 내가 구독한 or 나를 구독한 블로그의 포스트 정보 전달 시 블로그 정보 추가 전달
     **/
    public List<BlogItem> getPostOfBlogInfoList(List<Long> blogIdList) {

        List<Blog> blogList = blogRepository.findAllByBlogs(blogIdList);

        List<Long> memberIdList = blogList.stream()
                .map(Blog::getMemberId)
                .toList();

        List<String> memberNicknames = memberClient.findMemberNicknames(memberIdList);

        return BlogResponse.toBlogItems(memberNicknames, blogList);
    }

    // 구독 페이지에서 나를 구독하는 블로그의 정보
    public BlogItemList getSubscriberBlogList(Long blogId, int page) {

        // 나를 구독한 블로그의 사용자 아이디 목록
        Slice<Subscription> subscriberList = subscriptionQueryService.getMySubscribersSlice(blogId,
                page);
        List<Long> subscriberIds = subscriberList.stream()
                .map(Subscription::getFromMemberId)
                .toList();

        // 타블로그의 회원 아이디 목록
        List<Blog> blogs = blogRepository.findSubscriberIdList(subscriberIds);

        List<String> memberNicknames = getMemberNicknames(blogs);

        return BlogResponse.toBlogItemList(memberNicknames, blogs, subscriberList);

    }

    private List<String> getMemberNicknames(List<Blog> blogs) {

        List<Long> memberIdList = blogs.stream()
                .map(Blog::getMemberId)
                .toList();
        return memberClient.findMemberNicknames(memberIdList);
    }

    public Long findBlogIdByMemberId(Long memberId) {
        return blogRepository.findByMemberId(memberId).getId();
    }

    /**
     * Blog Search Result
     */
    public BlogInfoList searchBlogs(List<Long> blogIdList, int page) {

        PageRequest pageRequest = PageRequest.of(page, 12, Sort.by("createdAt"));

        Page<Blog> blogList = blogRepository.findAllByBlogList(blogIdList, pageRequest);

        return BlogResponse.toBlogInfoList(blogList);
    }

    /**
     * 블로그 페이지 - member, blog, post 정보 응답
     **/
    public BlogPage findBlogPage(Long blogId) {

        Blog blog = findById(blogId);

        BlogInfo blogInfo = toBlogInfo(blog);
        BlogPostItem blogPostItem = postClient.findBlogPostItem(blogId);
        String memberName = memberClient.findMemberName(blog.getMemberId());

        return toBlogpage(blogInfo, blogPostItem, memberName);
    }

    /**
     * 댓글 페이지 - 댓글 작성자의 블로그 정보 전달
     **/
    public List<CommentBlog> getCommentsBlog(List<Long> memberIdList) {

        List<Blog> blogList = blogRepository.findAllByMemberIdList(memberIdList);

        Map<Long, Blog> blogMap = blogList.stream()
                .collect(Collectors.toMap(Blog::getMemberId, blog -> blog));

        List<Blog> resultBlogs = new ArrayList<>();

        for (Long memberId : memberIdList) {
            Blog blog = blogMap.get(memberId);
            if (blog != null) {
                resultBlogs.add(blog);
            }
        }

        return resultBlogs.stream()
                .map(BlogResponse::toCommentBlog)
                .toList();
    }

    /**
     * 블로그 매칭 서비스
     **/
    public List<BlogRecommend> findRecommendBlog(Long memberId) {

        Long myBlogId = findBlogIdByMemberId(memberId);

        List<Long> subscriptionBlogIdList = subscriptionQueryService.getMySubscriptionBlogIdList(
                memberId);

        RecommendationRequest request = RecommendationDTO.toRecommendationRequest(myBlogId,
                subscriptionBlogIdList);

        List<Long> recommendBlogIdList = recommendationClient.getRecommendBlogs(request);

        return getRecommendBlogList(recommendBlogIdList);
    }

    private List<BlogRecommend> getRecommendBlogList(List<Long> blogIdList) {

        return blogRepository.findAllByBlogs(blogIdList)
                .stream()
                .map(BlogResponse::toBlogRecommend)
                .toList();
    }

}
