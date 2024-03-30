package com.justdo.plug.blog.domain.blog.service;

import com.justdo.plug.blog.domain.blog.Blog;
import com.justdo.plug.blog.domain.blog.dto.BlogResponse;
import com.justdo.plug.blog.domain.blog.dto.BlogResponse.ImageResult;
import com.justdo.plug.blog.domain.blog.repository.BlogRepository;
import com.justdo.plug.blog.global.exception.ApiException;
import com.justdo.plug.blog.global.response.code.status.ErrorStatus;
import com.justdo.plug.blog.global.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class BlogCommandService {

    private final BlogRepository blogRepository;
    private final S3Service s3Service;

    // 이미지 업로드
    public String uploadImage(MultipartFile multipartFile) {
        return s3Service.uploadFile(multipartFile).getImgUrl();
    }

    // 블로그 배경사진 수정
    public ImageResult editBackground(Long blogId, MultipartFile multipartFile) {

        // Blog 조회
        Blog blog = blogRepository.findById(blogId).orElseThrow(
            () -> new ApiException(ErrorStatus._BLOG_NOT_FOUND)
        );

        String imageUrl = uploadImage(multipartFile);
        blog.editBackgroud(imageUrl);

        return BlogResponse.toImageResult(imageUrl);
    }
}