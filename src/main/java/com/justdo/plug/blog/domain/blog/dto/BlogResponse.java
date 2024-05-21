package com.justdo.plug.blog.domain.blog.dto;

import com.justdo.plug.blog.domain.blog.Blog;
import com.justdo.plug.blog.domain.member.MemberDTO;
import com.justdo.plug.blog.domain.post.PostResponse.BlogPostItem;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

public class BlogResponse {

    @Schema(description = "사용자 및 블로그 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class MyBlogResult {

        private MemberDTO memberDTOInfo;
        private BlogInfo blogInfo;
    }

    public static MyBlogResult toMyBlogResult(MemberDTO memberDTOInfo, BlogInfo blogInfo) {
        return MyBlogResult.builder()
                .memberDTOInfo(memberDTOInfo)
                .blogInfo(blogInfo)
                .build();
    }

    @Schema(description = "블로그 정보 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class BlogInfo {

        @Schema(description = "블로그 제목")
        private String title;

        @Schema(description = "블로그 설명")
        private String description;

        @Schema(description = "블로그 프로필")
        private String profile;

        @Schema(description = "블로그 배경 사진")
        private String background;
    }

    public static BlogInfo toBlogInfo(Blog blog) {

        return BlogInfo.builder()
                .title(blog.getTitle())
                .description(blog.getDescription())
                .profile(blog.getProfile())
                .background(blog.getBackground())
                .build();
    }

    @Schema(description = "블로그 정보 응답 페이징 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class BlogInfoList {

        @Schema(description = "블로그 정보 목록")
        private List<BlogInfo> blogInfoList;

        @Schema(description = "페이징된 리스트의 항목 개수")
        private Integer listSize;

        @Schema(description = "총 페이징 수")
        private Integer totalPage;

        @Schema(description = "전체 데이터의 개수")
        private Long totalElements;

        @Schema(description = "첫 페이지의 여부")
        private Boolean isFirst;

        @Schema(description = "마지막 페이지의 여부")
        private Boolean isLast;
    }

    public static BlogInfoList toBlogInfoList(Page<Blog> blogs) {

        List<BlogInfo> blogInfoList = blogs.getContent()
                .stream()
                .map(BlogResponse::toBlogInfo)
                .toList();

        return BlogInfoList.builder()
                .blogInfoList(blogInfoList)
                .listSize(blogInfoList.size())
                .totalPage(blogs.getTotalPages())
                .isFirst(blogs.isFirst())
                .isLast(blogs.isLast())
                .build();
    }

    @Schema(description = "이미지 응답 DTO")
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class ImageResult {

        @Schema(description = "저장된 이미지 경로")
        private String imageUrl;
    }

    public static ImageResult toImageResult(String imageUrl) {
        return new ImageResult(imageUrl);
    }

    @Schema(description = "블로그 작성/수정/삭제 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class BlogProc {

        @Schema(description = "블로그 아이디")
        private Long blogId;

        @Schema(description = "응답 처리된 시간")
        private LocalDateTime createdAt;
    }

    public static BlogProc toBlogProc(Long blogId) {

        return BlogProc.builder()
                .blogId(blogId)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Schema(description = "구독 페이지 블로그 정보 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class BlogItem {

        @Schema(description = "블로그 아이디")
        private Long blogId;

        @Schema(description = "블로그 사용자의 닉네임")
        private String nickname;

        @Schema(description = "블로그의 제목")
        private String title;

        @Schema(description = "블로그 프로필")
        private String profile;
    }

    public static BlogItem toBlogItem(String nickname, Blog blog) {

        return BlogItem.builder()
                .blogId(blog.getId())
                .nickname(nickname)
                .title(blog.getTitle())
                .profile(blog.getProfile())
                .build();
    }

    @Schema(description = "구독 페이지 블로그의 정보 목록 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class BlogItemList {

        @Schema(description = "블로그 정보 목록")
        private List<BlogItem> blogItems;

        @Schema(description = "추가 목록이 있는 지의 여부")
        private Boolean hasNext;

        @Schema(description = "첫 페이지인지의 여부")
        private Boolean isFirst;

        @Schema(description = "마지막 페이지인지의 여부")
        private Boolean isLast;
    }

    public static BlogItemList toBlogItemList(List<String> nicknames, Slice<Blog> blogs) {

        List<BlogItem> blogItems = IntStream.range(0,
                        Math.min(nicknames.size(), blogs.getContent().size()))
                .mapToObj(i -> toBlogItem(nicknames.get(i), blogs.getContent().get(i)))
                .collect(Collectors.toList());

        return BlogItemList.builder()
                .blogItems(blogItems)
                .hasNext(blogs.hasNext())
                .isFirst(blogs.isFirst())
                .isLast(blogs.isLast())
                .build();
    }

    public static List<BlogItem> toBlogItems(List<String> nicknames, List<Blog> blogs) {

        return IntStream.range(0, blogs.size())
                .mapToObj(i -> toBlogItem(nicknames.get(i), blogs.get(i)))
                .toList();

    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class BlogPage {

        private BlogInfo blogInfo;
        private BlogPostItem blogPostItem;
        private String memberName;
    }

    public static BlogPage toBlogpage(BlogInfo blogInfo, BlogPostItem blogPostItem,
            String memberName) {

        return BlogPage.builder()
                .blogInfo(blogInfo)
                .blogPostItem(blogPostItem)
                .memberName(memberName)
                .build();
    }


}
