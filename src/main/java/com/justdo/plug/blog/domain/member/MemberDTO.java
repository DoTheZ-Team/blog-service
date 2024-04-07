package com.justdo.plug.blog.domain.member;

import com.justdo.plug.blog.domain.blog.dto.BlogRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDTO {

    private String email;
    private String nickname;
    private String profile_url;

    public static MemberDTO toMemberDTO(BlogRequest request) {
        return MemberDTO.builder()
            .email(request.getEmail())
            .nickname(request.getNickname())
            .profile_url(request.getProfile_url())
            .build();
    }
}
