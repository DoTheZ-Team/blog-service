package com.justdo.plug.blog.domain.member;

import com.justdo.plug.blog.global.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "member-service", url = "${application.config.members-url}", configuration = {
    FeignConfig.class})
public interface MemberClient {

    @GetMapping
    Member findMember();
}
