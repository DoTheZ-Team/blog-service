package com.justdo.plug.blog.domain.subscription.controller;

import com.justdo.plug.blog.domain.subscription.dto.SubscriptionResponse.SubscriptionProc;
import com.justdo.plug.blog.domain.subscription.service.SubscriptionCommandService;
import com.justdo.plug.blog.global.response.ApiResponse;
import com.justdo.plug.blog.global.utils.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blogs/subscription")
public class SubscriptionController {

    private final JwtProvider jwtProvider;
    private final SubscriptionCommandService subscriptionCommandService;

    @PostMapping("/{blogId}")
    public ApiResponse<SubscriptionProc> subscribe(HttpServletRequest request,
        @PathVariable(name = "blogId") Long blogId) {

        Long memberId = jwtProvider.getUserIdFromToken(request);

        return ApiResponse.onSuccess(subscriptionCommandService.subscribe(memberId, blogId));
    }
}
