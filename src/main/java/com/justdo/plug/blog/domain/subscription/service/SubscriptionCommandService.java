package com.justdo.plug.blog.domain.subscription.service;

import com.justdo.plug.blog.domain.subscription.Subscription;
import com.justdo.plug.blog.domain.subscription.dto.SubscriptionResponse;
import com.justdo.plug.blog.domain.subscription.dto.SubscriptionResponse.SubscriptionProc;
import com.justdo.plug.blog.domain.subscription.repository.SubscriptionRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SubscriptionCommandService {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionProc subscribe(Long memberId, Long blogId) {

        Optional<Subscription> subscription = getSubscription(memberId, blogId);

        return subscription.map(sub -> {
            sub.changeState();
            return SubscriptionResponse.toSubscriptionProc(sub);
        }).orElseGet(() -> {
            Subscription newSub = SubscriptionResponse.toEntity(memberId, blogId);
            save(newSub);
            return SubscriptionResponse.toSubscriptionProc(newSub);
        });
    }

    private void save(Subscription subscription) {
        subscriptionRepository.save(subscription);
    }

    public Optional<Subscription> getSubscription(Long memberId, Long blogId) {
        return subscriptionRepository.findByMemberIdAndBlogId(memberId, blogId);
    }
}
