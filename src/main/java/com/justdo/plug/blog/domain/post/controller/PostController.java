package com.justdo.plug.blog.domain.post.controller;

import com.justdo.plug.blog.domain.post.Post;
import com.justdo.plug.blog.domain.post.dto.PostRequestDto;
import com.justdo.plug.blog.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/blog/posts/")
@RequiredArgsConstructor
/*BLOG API - POSTS*/
public class PostController {

    private final PostService postService;

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);


    // BLOG001: 블로그 리스트 조회
    /* TODO: 서비스 함수 추가 및 return 해주기 */
    @GetMapping()
    public List<PostRequestDto> ViewList(){
        /*service*/
        return null;
    }

    // BLOG002: 블로그 상세페이지 조회
    @GetMapping("{post_id}")
    public PostRequestDto ViewPage(@PathVariable long post_id){
        /*service*/
        return null;
    }

    // BLOG003: 블로그 작성 요청
    @PostMapping("{blog_id}")
    public String PostBlog(@RequestBody PostRequestDto RequestDto, @PathVariable long blog_id) {
        try {
            RequestDto.setBlogId(blog_id);
            Post post = postService.save(RequestDto);
            return "success";
        } catch (Exception e) {
            logger.error("Failed to save post", e);
            return "Fail";
        }
    }


    // BLOG004: 블로그 수정 요청
    @PatchMapping("{post_id}")
    public PostRequestDto EditBlog(@PathVariable long post_id){
        /*service*/
        return null;
    }

    // BLOG005: 블로그 삭제 요청
    @DeleteMapping("{post_id}")
    public PostRequestDto DeleteBlog(@PathVariable long post_id){
        /*service*/
        return null;
    }


}
