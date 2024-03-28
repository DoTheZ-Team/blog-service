package com.justdo.plug.blog.domain.post.dto;
import com.justdo.plug.blog.domain.post.Post;
import lombok.*;

import java.time.LocalDateTime;
//
@Data
public class PostRequestDto {
    private String title;
    private String content;
    private int like_count;
    private boolean temporary_state;
    private boolean state;
    private long member_id;
    private long blog_id;


    /*POST 요청 DTO*/
    @Builder
    public PostRequestDto(String title, String content, boolean temporary_state, boolean state, LocalDateTime created_at, long member_id, long blog_id, LocalDateTime updated_at){
        this.title = title;
        this.content = content;
        this.temporary_state = temporary_state;
        this.state = state;
        this.member_id = member_id;
        this.blog_id = blog_id;
    }

    public void setBlogId(long blogId) {
        this.blog_id = blogId;
    }

    public Post toEntity(){
        return Post.builder().
        title(title).content(content).
        temporary_state(temporary_state).state(state).member_id(member_id).
                blog_id(blog_id).build();
    }

}
