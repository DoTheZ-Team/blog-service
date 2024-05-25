package com.justdo.plug.blog.domain.subscription.service;

import com.justdo.plug.blog.domain.post.PostClient;
import com.justdo.plug.blog.domain.post.PostResponse.PostItem;
import com.justdo.plug.blog.domain.post.PostResponse.PostItemList;
import com.justdo.plug.blog.domain.subscription.Subscription;
import com.justdo.plug.blog.domain.subscription.dto.SubscriptionRequest.LoginSubscription;
import com.justdo.plug.blog.domain.subscription.repository.SubscriptionRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SubscriptionQueryService {

    private final PostClient postClient;
    private final SubscriptionRepository subscriptionRepository;

    // 나의 구독 목록 조회
    public List<Subscription> getMySubscriptions(Long memberId) {

        return subscriptionRepository.findAllByFromMemberIdAndStateIsTrue(memberId);

    }

    public Slice<Subscription> getMySubscriptionsSlice(Long memberId, int page) {

        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("createdAt"));

        return subscriptionRepository.findSliceAllByFromMemberIdAndStateIsTrue(memberId,
                pageRequest);

    }

    // 구독한 블로그의 포스트 정보 가져오기
    public PostItemList getSubscriptionPostItems(List<Subscription> subscriptions, int page) {

        List<Long> blogIdList = subscriptions.stream()
                .map(Subscription::getToBlogId)
                .toList();

        return postClient.findSubscriptionFrom(blogIdList, page);
    }

    public List<Long> getBlogIdsFromPostItemList(PostItemList postItemList) {
        return postItemList.getPostItems().stream()
                .map(PostItem::getBlogId)
                .toList();
    }

    // 나의 구독자 목록 조회
    public List<Subscription> getMySubscribers(Long blogId) {

        return subscriptionRepository.findAllByToBlogIdAndStateIsTrue(blogId);
    }

    public Slice<Subscription> getMySubscribersSlice(Long blogId, int page) {

        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by("createdAt"));

        return subscriptionRepository.findSliceAllByToBlogIdAndStateIsTrue(blogId, pageRequest);
    }

    // 나를 구독하는 블로그의 포스트 정보 조회
    public PostItemList getSubscriberPostItems(List<Subscription> subscriptions, int page) {

        List<Long> subscriberIdList = subscriptions.stream()
                .map(Subscription::getFromMemberId)
                .toList();

        return postClient.findSubscriptionTo(subscriberIdList, page);
    }

    public Optional<Subscription> getSubscription(Long memberId, Long blogId) {
        return subscriptionRepository.findByFromMemberIdAndToBlogId(memberId, blogId);
    }

    public Boolean findLoginSubscribe(LoginSubscription loginSubscription) {

        return getSubscription(loginSubscription.getMemberId(),
                loginSubscription.getBlogId()).isPresent();
    }

    public List<Long> getMySubscriptionBlogIdList(Long memberId) {

        return subscriptionRepository.findAllByFromMemberIdAndStateIsTrue(memberId)
                .stream()
                .map(Subscription::getToBlogId)
                .toList();
    }
}
