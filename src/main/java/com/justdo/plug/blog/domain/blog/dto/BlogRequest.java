package com.justdo.plug.blog.domain.blog.dto;

import com.justdo.plug.blog.domain.blog.Blog;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BlogRequest {

    private String email;
    private String nickname;
    private String profileUrl;
    private String title;
    private String description;
    private String profile;
    private String background;

    public static Blog toEntity(Long id) {
        return Blog.builder()
            .memberId(id)
            .build();
    }
}
