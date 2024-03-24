package com.justdo.plug.blog.domain.post.service;

import com.justdo.plug.blog.domain.post.dto.PostDto;
import com.justdo.plug.blog.domain.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private PostRepository postRepository;
    @Autowired
    public PostService(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    /* TODO: 서비스 로직 추가 및 return 해주기 */
    // 블로그 리스트를 반환해주는 서비스
    public List<PostDto> ViewList(){
        return null;
    }




}
