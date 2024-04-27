package com.justdo.plug.blog.domain.subscription.dto;

import com.justdo.plug.blog.domain.subscription.Subscription;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class SubscriptionResponse {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class SubscriptionProc {

        private Long subscriptionId;
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
            .memberId(memberId)
            .blogId(blogId)
            .build();
    }
}
