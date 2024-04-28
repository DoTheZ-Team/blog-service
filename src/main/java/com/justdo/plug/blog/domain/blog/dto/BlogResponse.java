package com.justdo.plug.blog.domain.blog.dto;

import com.justdo.plug.blog.domain.blog.Blog;
import com.justdo.plug.blog.domain.member.MemberDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class BlogItem {

        private Long blogId;
        private String nickname;
        private String title;
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

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class BlogItemList {

        private List<BlogItem> blogItems;
        private boolean hasNext;
        private boolean hasFirst;
        private boolean hasLast;
    }

    public static BlogItemList toBlogItemList(List<String> nicknames, Slice<Blog> blogs) {

        List<BlogItem> blogItems = IntStream.range(0,
                Math.min(nicknames.size(), blogs.getContent().size()))
            .mapToObj(i -> toBlogItem(nicknames.get(i), blogs.getContent().get(i)))
            .collect(Collectors.toList());

        return BlogItemList.builder()
            .blogItems(blogItems)
            .hasNext(blogs.hasNext())
            .hasFirst(blogs.isFirst())
            .hasLast(blogs.isLast())
            .build();
    }


}
