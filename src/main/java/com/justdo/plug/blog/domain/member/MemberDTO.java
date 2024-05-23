package com.justdo.plug.blog.domain.member;

import com.justdo.plug.blog.domain.blog.dto.BlogRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "사용자 정보 응답 DTO")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDTO {

    @Schema(description = "사용자 이메일", example = "kakao@kakao.com")
    private String email;

    @Schema(description = "사용자 닉네임", example = "myNickname")
    private String nickname;

    @Schema(description = "사용자 프로필")
    private String profileUrl;

    public static MemberDTO toMemberDTO(BlogRequest request) {
        return MemberDTO.builder()
            .email(request.getEmail())
            .nickname(request.getNickname())
            .profileUrl(request.getProfileUrl())
            .build();
    }
}
