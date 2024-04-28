package com.justdo.plug.blog.domain.post;

import com.justdo.plug.blog.domain.post.PostResponse.PostItemList;
import com.justdo.plug.blog.global.config.FeignConfig;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "post-service", url = "${application.config.posts-url}", configuration = {
    FeignConfig.class})
public interface PostClient {


    @PostMapping("/preview")
    PostItemList findSubscriptionFrom(@RequestBody List<Long> blogIdList, @RequestParam int page);
}
