package com.justdo.plug.blog.global;

import com.justdo.plug.blog.domain.blog.Blog;
import com.justdo.plug.blog.domain.blog.repository.BlogRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final BlogRepository blogRepository;

    @PostConstruct
    @Transactional
    public void init() {

        Blog blog1 = Blog.builder()
            .title("test1")
            .background(
                "https://plug-bucket-01.s3.ap-northeast-2.amazonaws.com/_BERT+%E1%84%80%E1%85%AE%E1%84%8C%E1%85%A9.png")
            .memberId(1L)
            .build();

        blogRepository.save(blog1);
    }
}