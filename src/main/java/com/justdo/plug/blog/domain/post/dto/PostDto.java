package com.justdo.plug.blog.domain.post.dto;
import lombok.*;

import java.time.LocalDateTime;

@Data
public class PostDto {
    private int post_id;
    private char title;
    private String content;
    private int like_count;
    private boolean temporary_state;
    private boolean state;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private int member_id;
    private int blog_id;

    public PostDto(int post_id, char title, String content, int like_count, boolean temporary_state, boolean state, LocalDateTime created_at, int member_id, int blog_id, LocalDateTime updated_at){
        this.post_id = post_id;
        this.title = title;
        this.content = content;
        this.like_count = like_count;
        this.temporary_state = temporary_state;
        this.state = state;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.member_id = member_id;
        this.blog_id = blog_id;
    }

}
