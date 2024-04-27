package com.justdo.plug.blog.domain.blog.dto;

import com.justdo.plug.blog.domain.blog.Blog;
import com.justdo.plug.blog.domain.member.MemberDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
