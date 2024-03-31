package com.justdo.plug.blog.domain.hashtag.repository;

import com.justdo.plug.blog.domain.hashtag.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
    Hashtag findByName(String hashtagName);

}
