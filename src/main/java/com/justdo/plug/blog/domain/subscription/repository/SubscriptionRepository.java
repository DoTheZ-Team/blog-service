package com.justdo.plug.blog.domain.subscription.repository;

import com.justdo.plug.blog.domain.subscription.Subscription;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findByMemberIdAndBlogId(Long memberId, Long blogId);
}
