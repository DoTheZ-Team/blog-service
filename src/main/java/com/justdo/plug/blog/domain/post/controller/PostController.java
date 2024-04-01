package com.justdo.plug.blog.domain.post.controller;

import com.justdo.plug.blog.domain.category.Category;
import com.justdo.plug.blog.domain.category.service.CategoryService;
import com.justdo.plug.blog.domain.hashtag.service.HashtagService;
import com.justdo.plug.blog.domain.post.Post;
import com.justdo.plug.blog.domain.post.dto.PostRequestDto;
import com.justdo.plug.blog.domain.post.service.PostService;
import com.justdo.plug.blog.domain.posthashtag.PostHashtag;
import com.justdo.plug.blog.domain.posthashtag.service.PostHashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/blog/posts/")
@RequiredArgsConstructor
/*BLOG API - POSTS*/
public class PostController {

    private final HashtagService hashtagService;
    private final PostHashtagService postHashtagService;
    private final PostService postService;
    private final CategoryService categoryService;

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

            // 1. Post 에 저장
            RequestDto.setBlogId(blog_id);
            Post post = postService.save(RequestDto);

            // 2. Post_Hashtag 에 저장
            Long post_id = post.getId(); // Post 에서 post_id 받아오기

            List<String> hashtags = RequestDto.getHashtags(); // 해시태그 리스트 저장(2개)

            for (String hashtag : hashtags) {

                // 해시태그 이름으로 해시태그 ID를 가져오는 메서드
                Long hashtag_id = hashtagService.getHashtagIdByName(hashtag);
                
                // Post_Hashtag 엔티티 생성
                PostHashtag postHashtag = new PostHashtag();
                postHashtag.setPostId(post_id);
                postHashtag.setHashtagId(hashtag_id);

                // Post_Hashtag 엔티티 저장
                postHashtagService.save(postHashtag);
            }

            String name = RequestDto.getName(); // '카테고리 명'저장

            Category category = new Category();
            category.setPost_id(post_id);
            category.setName(name);

            categoryService.save(category);


            return "게시글이 성공적으로 업로드 되었습니다";
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
