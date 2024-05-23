package com.justdo.plug.blog.domain.recommendation;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class RecommendationDTO {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RecommendationRequest {

        private Long blogId;
        private List<Long> followsId;
    }

    public static RecommendationRequest toRecommendationRequest(Long blogId, List<Long> followsId) {

        return new RecommendationRequest(blogId, followsId);
    }

}
