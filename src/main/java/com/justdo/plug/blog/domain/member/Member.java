package com.justdo.plug.blog.domain.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {

    private String email;
    private String nickname;
    private String profile_url;
}
