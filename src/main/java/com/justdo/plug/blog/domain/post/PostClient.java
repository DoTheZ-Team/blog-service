package com.justdo.plug.blog.domain.post;

import com.justdo.plug.blog.domain.post.PostResponse.BlogPostItem;
import com.justdo.plug.blog.domain.post.PostResponse.PostItemList;
import com.justdo.plug.blog.global.config.FeignConfig;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "post-service", url = "${application.config.posts-url}", configuration = {
    FeignConfig.class})
public interface PostClient {


    @PostMapping("/previews/subscriptions")
    PostItemList findSubscriptionFrom(@RequestBody List<Long> blogIdList, @RequestParam int page);

    @PostMapping("/previews/subscribers")
    PostItemList findSubscriptionTo(@RequestBody List<Long> memberIdList, @RequestParam int page);

    @GetMapping("/blogs/{blogId}")
    BlogPostItem findBlogPostItem(@PathVariable Long blogId);
}
