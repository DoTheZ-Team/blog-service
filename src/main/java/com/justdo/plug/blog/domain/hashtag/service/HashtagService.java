package com.justdo.plug.blog.domain.hashtag.service;

import com.justdo.plug.blog.domain.hashtag.Hashtag;
import com.justdo.plug.blog.domain.hashtag.repository.HashtagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class HashtagService {
    private final HashtagRepository hashtagRepository;

    public Long getHashtagIdByName(String hashtagName) {
        Hashtag hashtag = hashtagRepository.findByName(hashtagName);
        if (hashtag != null) {
            return hashtag.getId();
        } else {
            return null;
        }
    }

}
