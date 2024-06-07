package com.justdo.plug.blog.domain.blog.repository;

import com.justdo.plug.blog.domain.blog.Blog;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE,
        connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
class BlogRepositoryTest {

    @Autowired
    private BlogRepository blogRepository;

    private Blog blog1, blog2, blog3;

    @BeforeEach
    void setUp() {
        blog1 = Blog.builder()
                .memberId(1L)
                .title("Blog 1")
                .build();

        blog2 = Blog.builder()
                .memberId(2L)
                .title("Blog 2")
                .build();

        blog3 = Blog.builder()
                .memberId(3L)
                .title("Blog 3")
                .build();


        blogRepository.saveAll(Arrays.asList(blog1, blog2, blog3));
    }

    @Test
    @DisplayName("블로그 id 리스트로 블로그 찾기")
    void testFindBlogIdList() {
        // Given
        List<Long> blogIds = Arrays.asList(blog1.getId(), blog2.getId());

        // When
        Slice<Blog> blogs = blogRepository.findBlogIdList(blogIds, PageRequest.of(0, 10));

        // Then
        assertThat(blogs).hasSize(2);
        assertThat(blogs.getContent()).containsExactlyInAnyOrder(blog1, blog2);
    }

    @Test
    @DisplayName("member id 리스트로 블로그 찾기")
    void testFindSubscriberIdList() {
        // Given
        List<Long> memberIds = Arrays.asList(1L, 3L);

        // When
        Slice<Blog> blogs = blogRepository.findSubscriberIdList(memberIds, PageRequest.of(0, 10));

        // Then
        assertThat(blogs).hasSize(2);
        assertThat(blogs.getContent()).containsExactlyInAnyOrder(blog1, blog3);
    }

    @Test
    @DisplayName("단일 member id로 블로그 찾기")
    void testFindByMemberId() {
        // Given
        Long memberId = 2L;

        // When
        Blog blog = blogRepository.findByMemberId(memberId);

        // Then
        assertThat(blog).isEqualTo(blog2);
    }

    @Test
    @DisplayName("모든 블로그 찾기")
    void testFindAllByBlogList() {
        // Given
        List<Long> blogIds = Arrays.asList(blog1.getId(), blog3.getId());

        // When
        Page<Blog> blogs = blogRepository.findAllByBlogList(blogIds, PageRequest.of(0, 10));

        // Then
        assertThat(blogs).hasSize(2);
        assertThat(blogs.getContent()).containsExactlyInAnyOrder(blog1, blog3);
    }

    @Test
    @DisplayName("빈 블로그 id 리스트로 블로그 찾기")
    void testFindBlogIdListEmpty() {
        // Given
        List<Long> blogIds = Collections.emptyList();

        // When
        Slice<Blog> blogs = blogRepository.findBlogIdList(blogIds, PageRequest.of(0, 10));

        // Then
        assertThat(blogs).isEmpty();
    }

    @Test
    @DisplayName("유효하지 않은 member id로 블로그 찾기")
    void testFindByInvalidMemberId() {
        // Given
        Long invalidMemberId = 999L;

        // When
        Blog blog = blogRepository.findByMemberId(invalidMemberId);

        // Then
        assertThat(blog).isNull();
    }

    @Test
    @DisplayName("대용량 블로그 찾기")
    void testFindLargeBlogList() {
        // Given
        for (long i = 4; i <= 100; i++) {
            Blog blog = Blog.builder()
                    .memberId(i)
                    .title("Blog " + i)
                    .build();
            blogRepository.save(blog);
        }
        List<Long> blogIds = blogRepository.findAll().stream().map(Blog::getId).toList();

        // When
        Page<Blog> blogs = blogRepository.findAllByBlogList(blogIds, PageRequest.of(0, 100));

        // Then
        assertThat(blogs).hasSize(100);
        assertThat(blogs.getTotalElements()).isEqualTo(100);
    }

    @Test
    @DisplayName("페이지 경계 조건 테스트")
    void testPaginationBoundaries() {
        // Given
        List<Long> blogIds = Arrays.asList(blog1.getId(), blog2.getId(), blog3.getId());

        // When
        Page<Blog> firstPage = blogRepository.findAllByBlogList(blogIds, PageRequest.of(0, 2));

        // Then
        assertThat(firstPage).hasSize(2);
        assertThat(firstPage.getTotalElements()).isEqualTo(3);
        assertThat(firstPage.getContent()).containsExactly(blog1, blog2);

        // When
        Page<Blog> secondPage = blogRepository.findAllByBlogList(blogIds, PageRequest.of(1, 2));

        // Then
        assertThat(secondPage).hasSize(1);
        assertThat(secondPage.getContent()).containsExactly(blog3);
    }
}