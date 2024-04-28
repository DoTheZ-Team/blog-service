package com.justdo.plug.blog.domain.subscription.dto;

import com.justdo.plug.blog.domain.blog.dto.BlogResponse;
import com.justdo.plug.blog.domain.blog.dto.BlogResponse.BlogItemList;
import com.justdo.plug.blog.domain.post.PostResponse;
import com.justdo.plug.blog.domain.post.PostResponse.PostItemList;
import com.justdo.plug.blog.domain.subscription.Subscription;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class SubscriptionResponse {

    @Schema(description = "구독 요청 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class SubscriptionProc {

        @Schema(description = "구독 고유 아이디")
        private Long subscriptionId;

        @Schema(description = "응답 처리 시간")
        private LocalDateTime createdAt;
    }

    public static SubscriptionProc toSubscriptionProc(Subscription subscription) {

        return SubscriptionProc.builder()
            .subscriptionId(subscription.getId())
            .createdAt(LocalDateTime.now())
            .build();
    }

    public static Subscription toEntity(Long memberId, Long blogId) {

        return Subscription.builder()
            .fromMemberId(memberId)
            .toBlogId(blogId)
            .build();
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class SubscriptionResult {

        BlogResponse.BlogItemList blogItemList;
        PostResponse.PostItemList postItemList;
    }

    public static SubscriptionResult toSubscriptionResult(BlogItemList blogItemList,
        PostItemList postItemList) {

        return SubscriptionResult.builder()
            .blogItemList(blogItemList)
            .postItemList(postItemList)
            .build();
    }
}
