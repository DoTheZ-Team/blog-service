package com.justdo.plug.blog.domain.post;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class PostResponse {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PostItem {

        private Long postId;
        private String title;
        private String preview;
        private String photo;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PostItemList {

        private List<PostItem> postItems;
        private boolean hasNext;
        private boolean hasFirst;
        private boolean hasLast;
    }

}
