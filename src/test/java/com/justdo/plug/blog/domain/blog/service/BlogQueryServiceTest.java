package com.justdo.plug.blog.domain.blog.service;

import com.justdo.plug.blog.domain.blog.Blog;
import com.justdo.plug.blog.domain.blog.dto.BlogResponse;
import com.justdo.plug.blog.domain.blog.repository.BlogRepository;
import com.justdo.plug.blog.domain.member.MemberClient;
import com.justdo.plug.blog.domain.member.MemberDTO;
import com.justdo.plug.blog.domain.post.PostClient;
import com.justdo.plug.blog.domain.subscription.service.SubscriptionQueryService;
import com.justdo.plug.blog.global.exception.ApiException;
import com.justdo.plug.blog.global.response.code.status.ErrorStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BlogQueryServiceTest {

    @Mock
    private BlogRepository blogRepository;

    @Mock
    private SubscriptionQueryService subscriptionQueryService;

    @Mock
    private MemberClient memberClient;

    @Mock
    private PostClient postClient;

    @InjectMocks
    private BlogQueryService blogQueryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("특정 블로그 정보 조회")
    void getBlogInfo_BlogExists_ReturnsMyBlogResult(){
        // Given
        Long memberId = 1L;
        Blog blog = Blog.builder()
                .memberId(memberId)
                .title("title")
                .description("description")
                .profile("profile")
                .background("background")
                .build();

        MemberDTO memberDTO = MemberDTO.builder()
                .email("email")
                .nickname("nickname")
                .profileUrl("profileUrl")
                .build();

        when(blogRepository.findById(blog.getId())).thenReturn(Optional.of(blog));
        when(memberClient.findMember()).thenReturn(memberDTO);

        // When
        BlogResponse.MyBlogResult result = blogQueryService.getBlogInfo(blog.getId());

        // Then
        assertNotNull(result);
        verify(blogRepository, times(1)).findById(blog.getId());
        verify(memberClient, times(1)).findMember();

    }

    @Test
    @DisplayName("존재하지 않는 블로그 id로 블로그 조회")
    void getBlogInfo_BlogNotFound_ThrowsApiException() {
        // Given
        Long blogId = 1L;
        when(blogRepository.findById(blogId)).thenReturn(Optional.empty());

        // When
        ApiException exception = assertThrows(ApiException.class, () -> {
            blogQueryService.getBlogInfo(blogId);
        });

        assertEquals(ErrorStatus._BLOG_NOT_FOUND.getCode(), exception.getErrorReason().getCode());
        verify(blogRepository, times(1)).findById(blogId);
    }


    @Test
    @DisplayName("memberId 기준 구독한 블로그 목록 조회")
    void getBlogInfoList_Success(){
        // Given
        Long memberId = 1L;
        int page = 0;

        List<Long> blogIdList = List.of(1L,2L,3L);
        List<Blog> blogs = List.of(
                Blog.builder().id(1L).memberId(1L).title("title1").build(),
                Blog.builder().id(2L).memberId(2L).title("title2").build(),
                Blog.builder().id(3L).memberId(3L).title("title3").build()
        );

        List<String> memberNicknames = List.of("nickname1", "nickname2", "nickname3");

        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("createdAt"));
        Slice<Blog> blogSlice = new SliceImpl<>(blogs, pageRequest, true);

        when(subscriptionQueryService.getSubscriptionBlogIdList(memberId, page)).thenReturn(blogIdList);
        when(blogRepository.findBlogIdList(blogIdList, pageRequest)).thenReturn(blogSlice);
        when(memberClient.findMemberNicknames(anyList())).thenReturn(memberNicknames);

        // When
        BlogResponse.BlogItemList result = blogQueryService.getBlogInfoList(memberId, page);

        // Then
        assertNotNull(result);
        assertEquals(3, result.getBlogItems().size());
        verify(subscriptionQueryService, times(1)).getSubscriptionBlogIdList(memberId, page);
        verify(blogRepository, times(1)).findBlogIdList(blogIdList, pageRequest);
        verify(memberClient, times(1)).findMemberNicknames(anyList());

    }

}