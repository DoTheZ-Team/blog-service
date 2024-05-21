package com.justdo.plug.blog.domain.subscription.service;

import com.justdo.plug.blog.domain.subscription.Subscription;
import com.justdo.plug.blog.domain.subscription.dto.SubscriptionResponse;
import com.justdo.plug.blog.domain.subscription.dto.SubscriptionResponse.SubscriptionProc;
import com.justdo.plug.blog.domain.subscription.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SubscriptionCommandService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionQueryService subscriptionQueryService;

    /** 로그인한 사용자 구독 버튼 클릭 **/
    public SubscriptionProc subscribe(Long memberId, Long blogId) {
        return subscriptionQueryService.getSubscription(memberId, blogId)
                .map(this::updateSubscription)
                .orElseGet(() -> createSubscription(memberId, blogId)); // Create new
    }

    /** 구독 데이터 on/off  **/
    private SubscriptionProc updateSubscription(Subscription existSub) {
        existSub.changeState();
        return SubscriptionResponse.toSubscriptionProc(existSub);
    }

    /** 구독 entity 생성 **/
    private SubscriptionProc createSubscription(Long memberId, Long blogId) {
        Subscription newSub = SubscriptionResponse.toEntity(memberId, blogId);
        save(newSub);
        return SubscriptionResponse.toSubscriptionProc(newSub);
    }

    private void save(Subscription subscription) {
        subscriptionRepository.save(subscription);
    }

}
