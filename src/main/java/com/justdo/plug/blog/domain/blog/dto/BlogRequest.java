package com.justdo.plug.blog.domain.blog.dto;

import lombok.Getter;

@Getter
public class BlogRequest {

    private String email;
    private String nickname;
    private String profile_url;
    private String title;
    private String description;
    private String profile;
    private String background;
}
