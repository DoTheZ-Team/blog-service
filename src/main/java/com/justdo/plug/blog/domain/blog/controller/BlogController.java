package com.justdo.plug.blog.domain.blog.controller;

import com.justdo.plug.blog.domain.blog.dto.BlogResponse.ImageResult;
import com.justdo.plug.blog.domain.blog.service.BlogCommandService;
import com.justdo.plug.blog.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/blogs")
@RequiredArgsConstructor
public class BlogController {

    private final BlogCommandService blogCommandService;

    @PatchMapping("/{blogId}/background")
    public ApiResponse<ImageResult> uploadBackground(@PathVariable Long blogId,
        @RequestPart(name = "imageUrl") MultipartFile multipartFile) {
        return ApiResponse.onSuccess(blogCommandService.editBackground(blogId, multipartFile));
    }
}