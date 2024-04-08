package com.justdo.plug.blog.domain.blog.controller;

import com.justdo.plug.blog.domain.blog.dto.BlogRequest;
import com.justdo.plug.blog.domain.blog.dto.BlogResponse.BlogProc;
import com.justdo.plug.blog.domain.blog.dto.BlogResponse.ImageResult;
import com.justdo.plug.blog.domain.blog.dto.BlogResponse.MyBlogResult;
import com.justdo.plug.blog.domain.blog.service.BlogCommandService;
import com.justdo.plug.blog.domain.blog.service.BlogQueryService;
import com.justdo.plug.blog.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/blogs")
@RequiredArgsConstructor
public class BlogController {

    private final BlogCommandService blogCommandService;
    private final BlogQueryService blogQueryService;

    @PostMapping("/images")
    public ApiResponse<ImageResult> uploadImage(
        @RequestPart(name = "imageUrl") MultipartFile multipartFile) {
        return ApiResponse.onSuccess(blogCommandService.imageUpload(multipartFile));
    }

    @GetMapping("/{blogId}")
    public ApiResponse<MyBlogResult> myBlog(@PathVariable(name = "blogId") Long blogId) {
        return ApiResponse.onSuccess(blogQueryService.getBlogInfo(blogId));
    }

    @PatchMapping("/{blogId}")
    public ApiResponse<BlogProc> updateBlog(@RequestBody BlogRequest request,
        @PathVariable(name = "blogId") Long blogId) {

        return ApiResponse.onSuccess(blogCommandService.updateBlog(request, blogId));
    }

    @PostMapping
    public ApiResponse<BlogProc> createBlog(@RequestParam Long memberId) {

        return ApiResponse.onSuccess(blogCommandService.createBlog(memberId));
    }
}
