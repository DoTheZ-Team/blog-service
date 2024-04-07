package com.justdo.plug.blog.domain.blog.dto;

import com.justdo.plug.blog.domain.blog.Blog;
import com.justdo.plug.blog.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class BlogResponse {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class MyBlogResult {

        private Member memberInfo;
        private BlogInfo blogInfo;
    }

    public static MyBlogResult toMyBlogResult(Member memberInfo, BlogInfo blogInfo) {
        return MyBlogResult.builder()
            .memberInfo(memberInfo)
            .blogInfo(blogInfo)
            .build();
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class BlogInfo {

        private String title;
        private String description;
        private String profile;
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

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class ImageResult {

        private String imageUrl;
    }

    public static ImageResult toImageResult(String imageUrl) {
        return new ImageResult(imageUrl);
    }
}
