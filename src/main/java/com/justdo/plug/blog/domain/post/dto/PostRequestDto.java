package com.justdo.plug.blog.domain.post.dto;

import com.justdo.plug.blog.domain.post.Post;
import lombok.*;

import java.util.List;

@Data
public class PostRequestDto {
    private String title;
    private String content;
    private int like_count;
    private boolean temporary_state;
    private boolean state;
    private long member_id;
    private long blog_id;
    @Getter
    private List<String> hashtags;
    @Getter
    private String name;
    @Getter
    private String photo_url;

    @Builder
    public PostRequestDto(String title, String content, boolean temporary_state, boolean state, long member_id, long blog_id,
                          List<String> hashtags, String name, String photo_url){

        this.title = title;
        this.content = content;
        this.temporary_state = temporary_state;
        this.state = state;
        this.member_id = member_id;
        this.blog_id = blog_id;
        this.hashtags = hashtags;
        this.name = name;
        this.photo_url = photo_url;
    }

    public void setBlogId(long blogId) {
        this.blog_id = blogId;
    }


    public Post toEntity(){
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
