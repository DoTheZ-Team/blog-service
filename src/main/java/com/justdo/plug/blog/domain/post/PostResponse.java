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

        @Schema(description = "포스트 아이디")
        private Long postId;

        @Schema(description = "포스트 제목")
        private String title;

        @Schema(description = "포스트 내용")
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
        private boolean hasNext;

        @Schema(description = "첫 페이지인지의 여부")
        private boolean hasFirst;

        @Schema(description = "마지막 페이지인지의 여부")
        private boolean hasLast;
    }

}
