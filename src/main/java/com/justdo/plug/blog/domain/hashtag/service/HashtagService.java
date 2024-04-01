package com.justdo.plug.blog.domain.hashtag.service;

import com.justdo.plug.blog.domain.hashtag.Hashtag;
import com.justdo.plug.blog.domain.hashtag.repository.HashtagRepository;
import com.justdo.plug.blog.global.exception.ApiException;
import com.justdo.plug.blog.global.response.code.status.ErrorStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class HashtagService {
    private final HashtagRepository hashtagRepository;

    public Long getHashtagIdByName(String hashtagName) {
        Optional<Hashtag> optionalHashtag = Optional.ofNullable(hashtagRepository.findByName(hashtagName));
        Hashtag hashtag = optionalHashtag.orElseThrow(() -> new ApiException(ErrorStatus._INTERNAL_SERVER_ERROR));
        return hashtag.getId();
    }
}
