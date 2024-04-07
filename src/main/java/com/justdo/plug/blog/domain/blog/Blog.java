package com.justdo.plug.blog.domain.blog;

import com.justdo.plug.blog.domain.common.BaseTimeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Blog extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    private String title = "블로그 제목을 입력해주세요.";

    private String description;

    private String profile;

    private String background;

    private Long memberId;

    /**
     * update 함수
     */
    public void editBackgroud(String background) {
        this.background = background;
    }
}
