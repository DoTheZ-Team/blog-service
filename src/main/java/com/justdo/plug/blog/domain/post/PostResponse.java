package com.justdo.plug.blog.domain.post;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class PostResponse {

    @Schema(description = "포스트 정보 DTO")
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PostItem {

        @Schema(description = "블로그 아이디", example = "1")
        private Long blogId;

        @Schema(description = "포스트 아이디", example = "1")
        private Long postId;

        @Schema(description = "포스트 제목", example = "오늘은 목요일 일상")
        private String title;

        @Schema(description = "포스트 내용", example = "오늘은 목요일 포스트 내용, 무엇을 먹을까요?")
        private String preview;

        @Schema(description = "포스트 메인 사진")
        private String photo;
    }

    @Schema(description = "포스트 정보 목록 DTO")
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PostItemList {

        @Schema(description = "포스트 정보 목록")
        private List<PostItem> postItems;

        @Schema(description = "추가 목록이 있는 지의 여부")
        private Boolean hasNext;

        @Schema(description = "첫 페이지인지의 여부")
        private Boolean isFirst;

        @Schema(description = "마지막 페이지인지의 여부")
        private Boolean isLast;
    }

    @Schema(description = "포스트 정보(사진 5개) 목록 DTO")
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PostItemBy5Photo {

        @Schema(description = "Blog 아이디")
        private Long blogId;

        @Schema(description = "Post 아이디")
        private Long postId;

        @Schema(description = "Post 제목")
        private String title;

        @Schema(description = "Post 글 미리보기")
        private String preview;

        @Schema(description = "Post의 추가된 사진 경로 리스트")
        private List<String> photoUrl;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class BlogPostItem {

        private List<PostItemBy5Photo> postItems;
        private List<String> hashtagNames;
    }

}
