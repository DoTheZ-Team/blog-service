package com.justdo.plug.blog.domain.post.service;

import com.justdo.plug.blog.domain.post.Post;
import com.justdo.plug.blog.domain.post.dto.PostRequestDto;
import com.justdo.plug.blog.domain.post.repository.PostRepository;
import com.justdo.plug.blog.global.exception.ApiException;
import com.justdo.plug.blog.global.response.code.status.ErrorStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    // 블로그 작성
    public Post save(PostRequestDto requestDto) {
        try {
            Post post = requestDto.toEntity();
            return postRepository.save(post);
        } catch (Exception e) {
            throw new ApiException(ErrorStatus._INTERNAL_SERVER_ERROR
            );
        }
    }


}
