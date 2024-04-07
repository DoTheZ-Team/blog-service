package com.justdo.plug.blog.domain.blog.service;

import com.justdo.plug.blog.domain.blog.dto.BlogResponse;
import com.justdo.plug.blog.domain.blog.dto.BlogResponse.ImageResult;
import com.justdo.plug.blog.domain.blog.repository.BlogRepository;
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


    // 블로그 배경사진 수정
    public ImageResult imageUpload(MultipartFile multipartFile) {

        String imageUrl = s3Service.uploadFile(multipartFile);
        return BlogResponse.toImageResult(imageUrl);
    }
}
