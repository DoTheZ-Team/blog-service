package com.justdo.plug.blog.domain.post.service;

import com.justdo.plug.blog.domain.post.Post;
import com.justdo.plug.blog.domain.post.dto.PostRequestDto;
import com.justdo.plug.blog.domain.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    /* TODO: 서비스 로직 추가 및 return 해주기 */
    // 블로그 작성
    public Post save(PostRequestDto requestDto){
        Post post = requestDto.toEntity();
        return postRepository.save(post);
    }


}
