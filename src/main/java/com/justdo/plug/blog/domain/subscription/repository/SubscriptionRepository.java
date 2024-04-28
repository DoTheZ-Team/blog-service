package com.justdo.plug.blog.domain.subscription.repository;

import com.justdo.plug.blog.domain.subscription.Subscription;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findByFromMemberIdAndToBlogId(Long fromMemberId, Long toBlogId);

    List<Subscription> findAllByFromMemberId(Long fromMemberId, PageRequest pageRequest);

    List<Subscription> findAllByToBlogId(Long toBlogId, PageRequest pageRequest);
}
