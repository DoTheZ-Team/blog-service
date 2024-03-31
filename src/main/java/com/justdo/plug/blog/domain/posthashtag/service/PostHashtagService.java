package com.justdo.plug.blog.domain.posthashtag.service;

import com.justdo.plug.blog.domain.posthashtag.PostHashtag;
import com.justdo.plug.blog.domain.posthashtag.repository.PostHashtagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class PostHashtagService {
    private final PostHashtagRepository postHashtagRepository;

    public void save(PostHashtag postHashtag) {
        postHashtagRepository.save(postHashtag);
    }
}
