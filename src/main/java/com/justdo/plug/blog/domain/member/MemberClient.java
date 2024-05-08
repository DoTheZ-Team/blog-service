package com.justdo.plug.blog.domain.member;

import com.justdo.plug.blog.global.config.FeignConfig;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "auth-service", url = "${application.config.auths-url}", configuration = {
    FeignConfig.class})
public interface MemberClient {

    @GetMapping
    MemberDTO findMember();

    @PutMapping
    void updateMember(@RequestBody MemberDTO memberDTO);

    @PostMapping("/blogs")
    List<String> findMemberNicknames(@RequestBody List<Long> memberIdList);
}
