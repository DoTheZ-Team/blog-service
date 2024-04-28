package com.justdo.plug.blog.domain.subscription.service;

import com.justdo.plug.blog.domain.post.PostClient;
import com.justdo.plug.blog.domain.post.PostResponse.PostItemList;
import com.justdo.plug.blog.domain.subscription.Subscription;
import com.justdo.plug.blog.domain.subscription.repository.SubscriptionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubscriptionQueryService {

    private final PostClient postClient;
    private final SubscriptionRepository subscriptionRepository;

    public PostItemList getSubscriptionPostFrom(Long memberId, int page) {

        List<Long> blogIdList = getSubscriptionBlogIdList(memberId, page);

        return postClient.findSubscriptionFrom(blogIdList, page);
    }

    // 나를 구독하는 블로그의 포스트 정보 조회
    public PostItemList getSubscriptionPostTo(Long blogId, int page) {

        List<Long> subscriberIdList = getSubscriberBlogIdList(blogId, page);

        return postClient.findSubscriptionTo(subscriberIdList, page);
    }

    // 내가 구독하는 블로그 사용자의 아이디 목록 조회
    public List<Long> getSubscriptionBlogIdList(Long memberId, int page) {

        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("createdAt"));

        return subscriptionRepository.findAllByFromMemberId(memberId, pageRequest)
            .stream()
            .map(Subscription::getToBlogId)
            .toList();
    }

    // 나를 구독하는 블로그 사용자의 아이디 목록 조회
    public List<Long> getSubscriberBlogIdList(Long blogId, int page) {

        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("createdAt"));
        return subscriptionRepository.findAllByToBlogId(blogId, pageRequest)
            .stream()
            .map(Subscription::getFromMemberId)
            .toList();

    }
}
