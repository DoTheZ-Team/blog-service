package com.justdo.plug.blog.domain.subscription.dto;

import com.justdo.plug.blog.domain.blog.dto.BlogResponse;
import com.justdo.plug.blog.domain.blog.dto.BlogResponse.BlogItem;
import com.justdo.plug.blog.domain.blog.dto.BlogResponse.BlogItemList;
import com.justdo.plug.blog.domain.post.PostResponse.PostItem;
import com.justdo.plug.blog.domain.post.PostResponse.PostItemList;
import com.justdo.plug.blog.domain.subscription.Subscription;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;
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

    @Schema(description = "내가 구독한 블로그의 모든 정보 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class SubscriptionResult {

        private BlogResponse.BlogItemList blogItemList;
        private BlogPostItem blogPostItem;
    }

    public static SubscriptionResult toSubscriptionResult(BlogItemList blogItemList,
            BlogPostItem blogPostItem) {

        return SubscriptionResult.builder()
                .blogItemList(blogItemList)
                .blogPostItem(blogPostItem)
                .build();
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class BlogPostPreview {

        private BlogItem blogItem;
        private PostItem postItem;
    }

    public static BlogPostPreview toBlogPostPreview(PostItem postItem,
            BlogItem blogItem) {

        return BlogPostPreview.builder()
                .blogItem(blogItem)
                .postItem(postItem)
                .build();
    }

    @Schema(name = "SubscribedBlogPostItem", description = "내가 구독한 블로그의 포스트 정보 응답 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class BlogPostItem {

        @Schema(description = "구독한 블로그 정보와 포스트 정보가 담긴 DTO")
        private List<BlogPostPreview> blogPostPreviews;

        @Schema(description = "추가 목록이 있는 지의 여부")
        private Boolean hasNext;

        @Schema(description = "첫 페이지인지의 여부")
        private Boolean isFirst;

        @Schema(description = "마지막 페이지인지의 여부")
        private Boolean isLast;
    }

    public static BlogPostItem toBlogPostItem(PostItemList postItemList,
            List<BlogItem> blogItemList) {

        List<PostItem> postItems = postItemList.getPostItems();

        List<BlogPostPreview> blogPostPreviews = IntStream.range(0,
                        postItems.size())
                .mapToObj(i -> toBlogPostPreview(postItems.get(i), blogItemList.get(i)))
                .toList();

        return BlogPostItem.builder()
                .blogPostPreviews(blogPostPreviews)
                .hasNext(postItemList.getHasNext())
                .isFirst(postItemList.getIsFirst())
                .isLast(postItemList.getIsLast())
                .build();
    }
}

