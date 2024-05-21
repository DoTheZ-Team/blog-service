package com.justdo.plug.blog.domain.subscription.controller;

import com.justdo.plug.blog.domain.blog.dto.BlogResponse.BlogItem;
import com.justdo.plug.blog.domain.blog.dto.BlogResponse.BlogItemList;
import com.justdo.plug.blog.domain.blog.service.BlogQueryService;
import com.justdo.plug.blog.domain.post.PostResponse.PostItemList;
import com.justdo.plug.blog.domain.subscription.dto.SubscriptionRequest;
import com.justdo.plug.blog.domain.subscription.dto.SubscriptionResponse;
import com.justdo.plug.blog.domain.subscription.dto.SubscriptionResponse.BlogPostItem;
import com.justdo.plug.blog.domain.subscription.dto.SubscriptionResponse.SubscriptionProc;
import com.justdo.plug.blog.domain.subscription.service.SubscriptionCommandService;
import com.justdo.plug.blog.domain.subscription.service.SubscriptionQueryService;
import com.justdo.plug.blog.global.response.ApiResponse;
import com.justdo.plug.blog.global.utils.JwtProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Subscription 관련 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/blogs/subscriptions")
public class SubscriptionController {

    private final JwtProvider jwtProvider;
    private final SubscriptionCommandService subscriptionCommandService;
    private final SubscriptionQueryService subscriptionQueryService;
    private final BlogQueryService blogQueryService;

    @Operation(summary = "블로그 페이지 - 블로그 구독 요청 및 취소 요청", description = "다른 블로그를 구독 요청합니다. / 구독 상태에서 누르면 취소됩니다.")
    @Parameter(name = "blogId", description = "블로그 Id, Path Variable입니다.", required = true, example = "1", in = ParameterIn.PATH)
    @PostMapping("/{blogId}")
    public ApiResponse<SubscriptionProc> subscribe(HttpServletRequest request,
            @PathVariable(name = "blogId") Long blogId) {

        Long memberId = jwtProvider.getUserIdFromToken(request);

        return ApiResponse.onSuccess(subscriptionCommandService.subscribe(memberId, blogId));
    }

    @Operation(summary = "구독 페이지 - 내가 구독한 블로그와 포스트 정보 모두 조회", description = "내가 구독한 블로그와 포스트를 조회합니다.")
    @Parameter(name = "page", description = "페이지 번호, Query Parameter입니다.", example = "0", in = ParameterIn.QUERY)
    @GetMapping
    public ApiResponse<SubscriptionResponse.SubscriptionResult> getSubscriptionFrom(
            HttpServletRequest request, @RequestParam(defaultValue = "0") int page) {

        Long memberId = jwtProvider.getUserIdFromToken(request);

        /** 왼쪽 구독한 블로그 정보 **/
        BlogItemList blogItemList = blogQueryService.getBlogInfoList(memberId, page);

        /** 오른쪽 구독한 블로그의 포스트 정보**/
        PostItemList postItemList = subscriptionQueryService.getSubscriptionPostFrom(
                memberId, page);
        List<Long> postOfBlogIds = subscriptionQueryService.getPostOfBlogIds(postItemList);
        List<BlogItem> blogItems = blogQueryService.getPostOfBlogInfoList(postOfBlogIds);
        BlogPostItem blogPostItem = SubscriptionResponse.toBlogPostItem(postItemList,
                blogItems);

        return ApiResponse.onSuccess(
                SubscriptionResponse.toSubscriptionResult(blogItemList, blogPostItem));
    }

    @Operation(summary = "구독 페이지 - 내가 구독한 블로그 정보 조회", description = "내가 구독한 블로그의 정보를 조회합니다.")
    @Parameter(name = "page", description = "페이지 번호, Query Parameter입니다.", example = "0", in = ParameterIn.QUERY)
    @GetMapping("/follows")
    public ApiResponse<BlogItemList> getFollows(HttpServletRequest request,
            @RequestParam(defaultValue = "0") int page) {

        Long memberId = jwtProvider.getUserIdFromToken(request);
        return ApiResponse.onSuccess(blogQueryService.getBlogInfoList(memberId, page));
    }

    @Operation(summary = "구독 페이지 - 내가 구독한 블로그의 포스트 정보 조회", description = "내가 구독한 블로그의 포스트 정보를 조회합니다.")
    @Parameter(name = "page", description = "페이지 번호, Query Parameter입니다.", example = "0", in = ParameterIn.QUERY)
    @GetMapping("/follows/posts")
    public ApiResponse<BlogPostItem> getFollowsPosts(HttpServletRequest request,
            @RequestParam(defaultValue = "0") int page) {

        Long memberId = jwtProvider.getUserIdFromToken(request);
        PostItemList postItemList = subscriptionQueryService.getSubscriptionPostFrom(
                memberId, page);
        List<Long> postOfBlogIds = subscriptionQueryService.getPostOfBlogIds(postItemList);
        List<BlogItem> blogItemList = blogQueryService.getPostOfBlogInfoList(postOfBlogIds);

        return ApiResponse.onSuccess(
                SubscriptionResponse.toBlogPostItem(postItemList, blogItemList));
    }

//    @Operation(summary = "구독 페이지 - 나를 구독한 블로그와 포스트 모두 정보 조회", description = "나를 구독한 블로그와 포스트 정보를 모두 조회합니다.")
//    @Parameter(name = "page", description = "페이지 번호, Query Parameter입니다.", example = "0", in = ParameterIn.QUERY)
//    @GetMapping("/subscribers")
//    public ApiResponse<SubscriptionResponse.SubscriptionResult> getSubscriptionTo(
//        HttpServletRequest request, @RequestParam(defaultValue = "0") int page) {
//
//        Long memberId = jwtProvider.getUserIdFromToken(request);
//        Long blogId = blogQueryService.findBlogIdByMemberId(memberId);
//
//        BlogItemList blogInfoList = blogQueryService.getSubscriberBlogList(blogId, page);
//        PostItemList postItemList = subscriptionQueryService.getSubscriptionPostTo(
//            blogId, page);
//
//        return ApiResponse.onSuccess(
//            SubscriptionResponse.toSubscriptionResult(blogInfoList, postItemList));
//    }
//
//    @Operation(summary = "구독 페이지 - 나를 구독한 블로그 정보 조회", description = "나를 구독한 블로그 정보를 조회합니다.")
//    @Parameter(name = "page", description = "페이지 번호, Query Parameter입니다.", example = "0", in = ParameterIn.QUERY)
//    @GetMapping("/followers")
//    public ApiResponse<BlogItemList> getFollowers(HttpServletRequest request,
//        @RequestParam(defaultValue = "0") int page) {
//
//        Long memberId = jwtProvider.getUserIdFromToken(request);
//        Long blogId = blogQueryService.findBlogIdByMemberId(memberId);
//
//        return ApiResponse.onSuccess(blogQueryService.getSubscriberBlogList(blogId, page));
//    }
//
//    @Operation(summary = "구독 페이지 - 나를 구독한 블로그의 포스트 정보 조회", description = "나를 구독한 블로그의 포스트 정보를 조회합니다.")
//    @Parameter(name = "page", description = "페이지 번호, Query Parameter입니다.", example = "0", in = ParameterIn.QUERY)
//    @GetMapping("/followers/posts")
//    public ApiResponse<PostItemList> getFollowersPosts(HttpServletRequest request,
//        @RequestParam(defaultValue = "0") int page) {
//
//        Long memberId = jwtProvider.getUserIdFromToken(request);
//        Long blogId = blogQueryService.findBlogIdByMemberId(memberId);
//
//        return ApiResponse.onSuccess(subscriptionQueryService.getSubscriptionPostTo(
//            blogId, page));
//    }

    @Operation(summary = "구독 페이지 - Open feign요청입니다.(로그인한 사용자가 구독한 블로그인지 확인)", description = "로그인한 사용자가 구독한 블로그인지 확인합니다.")
    @PostMapping
    public Boolean getLoginSubscribed(
            @RequestBody SubscriptionRequest.LoginSubscription loginSubscription) {
        return subscriptionQueryService.findLoginSubscribe(loginSubscription);
    }
}
