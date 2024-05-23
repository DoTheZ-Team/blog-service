package com.justdo.plug.blog.domain.recommendation;

import com.justdo.plug.blog.domain.recommendation.RecommendationDTO.RecommendationRequest;
import com.justdo.plug.blog.global.config.FeignConfig;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "recommend-service", url = "${application.config.recommends-url}", configuration = {
        FeignConfig.class})
public interface RecommendationClient {

    @PostMapping
    List<Long> getRecommendBlogs(@RequestBody RecommendationRequest request);
}
