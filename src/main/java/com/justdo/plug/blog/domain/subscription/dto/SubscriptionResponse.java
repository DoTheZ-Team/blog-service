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
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
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

    public static BlogPostPreview toBlogPostPreview(BlogItem blogItem, PostItem postItem) {

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

        List<BlogPostPreview> blogPostPreviews = getBlogPostPreviews(postItemList.getPostItems(),
                blogItemList);

        return BlogPostItem.builder()
                .blogPostPreviews(blogPostPreviews)
                .hasNext(postItemList.getHasNext())
                .isFirst(postItemList.getIsFirst())
                .isLast(postItemList.getIsLast())
                .build();
    }

    private static List<BlogPostPreview> getBlogPostPreviews(List<PostItem> postItemList,
            List<BlogItem> blogItemList) {

        Map<Long, BlogItem> blogItemMap = blogItemList.stream()
                .collect(Collectors.toMap(BlogItem::getBlogId, Function.identity()));

        return postItemList.stream()
                .map(postItem -> {
                    BlogItem blogItem = blogItemMap.get(postItem.getBlogId());
                    return toBlogPostPreview(blogItem, postItem);
                })
                .collect(Collectors.toList());
    }

    @Schema(description = "구독 확인 여부 및 프로필 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class SubscribedProfile {

        private boolean isSubscribed;

        private String profile;
    }

    public static SubscribedProfile toSubscribedProfile(boolean isSubscribed, String profile) {

        return SubscribedProfile.builder()
                .isSubscribed(isSubscribed)
                .profile(profile)
                .build();
    }

}

