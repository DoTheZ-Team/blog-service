package com.justdo.plug.blog.domain.member;

import com.justdo.plug.blog.global.config.FeignConfig;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "AUTH-SERVICE", configuration = {FeignConfig.class})
public interface MemberClient {

    @GetMapping("/auths")
    MemberDTO findMember();

    @PutMapping("/auths")
    void updateMember(@RequestBody MemberDTO memberDTO);

    @PostMapping("/auths/blogs")
    List<String> findMemberNicknames(@RequestBody List<Long> memberIdList);
}
