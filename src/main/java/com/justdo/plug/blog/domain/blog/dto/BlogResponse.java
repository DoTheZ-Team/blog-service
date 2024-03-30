package com.justdo.plug.blog.domain.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class BlogResponse {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class ImageResult {

        String imageUrl;
    }

    public static ImageResult toImageResult(String imageUrl) {
        return new ImageResult(imageUrl);
    }
}
