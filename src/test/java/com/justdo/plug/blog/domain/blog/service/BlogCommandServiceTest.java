package com.justdo.plug.blog.domain.blog.service;

import com.justdo.plug.blog.domain.blog.Blog;
import com.justdo.plug.blog.domain.blog.dto.BlogRequest;
import com.justdo.plug.blog.domain.blog.dto.BlogResponse;
import com.justdo.plug.blog.domain.blog.repository.BlogRepository;
import com.justdo.plug.blog.domain.member.MemberClient;
import com.justdo.plug.blog.global.s3.S3Service;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BlogCommandServiceTest {

    @InjectMocks
    private BlogCommandService blogCommandService;

    @Mock
    private BlogRepository blogRepository;

    @Mock
    private BlogQueryService blogQueryService;

    @Mock
    private MemberClient memberClient;

    @Mock
    private S3Service s3Service;

    private static MockedStatic<BlogRequest> blogRequestMockedStatic;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    public void beforeClass() {
        blogRequestMockedStatic = mockStatic(BlogRequest.class);
    }

    @AfterEach
    public void afterClass() {
        blogRequestMockedStatic.close();
    }

    @Test
    @DisplayName("이미지 업로드")
    void testImageUpload(){
        // Given
        MultipartFile multipartFile = mock(MultipartFile.class);
        String expectedUrl = "http://s3.amazonaws.com/image.jpg";
        when(s3Service.uploadFile(multipartFile)).thenReturn(expectedUrl);

        // When
        BlogResponse.ImageResult result = blogCommandService.imageUpload(multipartFile);

        // Then
        assertEquals(expectedUrl,result.getImageUrl());
        verify(s3Service,times(1)).uploadFile(multipartFile);
    }

    @Test
    @DisplayName("블로그 업데이트")
    void testUpdateBlog(){
        // Given
        Long blogId = 1L;
        BlogRequest request = new BlogRequest();
        ReflectionTestUtils.setField(request, "title", "New Title");
        ReflectionTestUtils.setField(request, "description", "New Description");
        ReflectionTestUtils.setField(request, "profile", "New Profile");
        ReflectionTestUtils.setField(request, "background", "New Background");
        ReflectionTestUtils.setField(request, "email", "test@example.com");
        ReflectionTestUtils.setField(request, "nickname", "nickname");
        ReflectionTestUtils.setField(request, "profileUrl", "profileUrl");

        Blog blog = mock(Blog.class);
        when(blogQueryService.findById(blogId)).thenReturn(blog);

        // When
        BlogResponse.BlogProc result = blogCommandService.updateBlog(request, blogId);

        // Then
        assertEquals(blogId, result.getBlogId());
        verify(memberClient, times(1)).updateMember(any());
        verify(blogQueryService, times(1)).findById(blogId);
        verify(blog, times(1)).update(request);

    }

    @Test
    @DisplayName("유효한 memberId로 블로그 생성")
    void testCreateBlog() {
        // Given
        Long memberId = 1L;
        Blog blog = Blog.builder()
                .memberId(memberId)
                .title("title")
                .description("description")
                .profile("profile")
                .background("background")
                .build();

        when(BlogRequest.toEntity(memberId)).thenReturn(blog);

        // When
        BlogResponse.BlogProc result = blogCommandService.createBlog(memberId);

        // Then
        assertNotNull(result);
        assertEquals(blog.getId(), result.getBlogId());
        verify(blogRepository, times(1)).save(blog);
    }

}