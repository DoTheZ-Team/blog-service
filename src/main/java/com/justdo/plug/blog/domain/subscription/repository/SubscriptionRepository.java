package com.justdo.plug.blog.domain.subscription.repository;

import com.justdo.plug.blog.domain.subscription.Subscription;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findByFromMemberIdAndToBlogId(Long fromMemberId, Long toBlogId);

    List<Subscription> findAllByFromMemberIdAndStateIsTrue(Long fromMemberId);

    Slice<Subscription> findSliceAllByFromMemberIdAndStateIsTrue(Long fromMemberId,
            PageRequest pageRequest);

    List<Subscription> findAllByToBlogIdAndStateIsTrue(Long toBlogId);

    Slice<Subscription> findSliceAllByToBlogIdAndStateIsTrue(Long toBlogId,
            PageRequest pageRequest);

}
