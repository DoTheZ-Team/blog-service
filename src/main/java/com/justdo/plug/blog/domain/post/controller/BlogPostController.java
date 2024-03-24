package com.justdo.plug.blog.domain.post.controller;

import com.justdo.plug.blog.domain.post.dto.PostDto;
import com.justdo.plug.blog.domain.post.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
/*BLOG API - POSTS*/
public class BlogPostController {

    private final PostService postService;

    public BlogPostController(PostService postService) {
        this.postService = postService;
    }

    // BLOG001: 블로그 리스트 조회
    /* TODO: 서비스 함수 추가 및 return 해주기 */
    @GetMapping()
    public List<PostDto> ViewList(){
        /*service*/
        return null;
    }

    // BLOG002: 블로그 상세페이지 조회
    @GetMapping("{id}")
    public PostDto ViewPage(@PathVariable int post_id){
        /*service*/
        return null;
    }

    // BLOG003: 블로그 작성 요청
    @PostMapping("{id}")
    public void PostBlog(@PathVariable int post_id){
        /*service*/
    }

    // BLOG004: 블로그 수정 요청
    @PatchMapping("{id}")
    public PostDto EditBlog(@PathVariable int post_id){
        /*service*/
        return null;
    }

    // BLOG005: 블로그 삭제 요청
    @DeleteMapping("{id}")
    public PostDto DeleteBlog(@PathVariable int post_id){
        /*service*/
        return null;
    }


}
