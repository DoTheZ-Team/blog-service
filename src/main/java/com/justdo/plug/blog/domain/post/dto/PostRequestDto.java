package com.justdo.plug.blog.domain.post.dto;

import com.justdo.plug.blog.domain.post.Post;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostRequestDto {
    private String title;
    private String content;
    private int like_count;
    private boolean temporary_state;
    private boolean state;
    private long member_id;
    private long blog_id;

    public Post toEntity() {
        return Post.builder()
                .title(title)
                .content(content)
                .temporary_state(temporary_state)
                .state(state)
                .member_id(member_id)
                .blog_id(blog_id)
                .build();
    }
}
