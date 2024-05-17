package com.justdo.plug.blog.domain.blog.controller;

import com.justdo.plug.blog.domain.blog.dto.BlogRequest;
import com.justdo.plug.blog.domain.blog.dto.BlogResponse.BlogComment;
import com.justdo.plug.blog.domain.blog.dto.BlogResponse.BlogInfoList;
import com.justdo.plug.blog.domain.blog.dto.BlogResponse.BlogPage;
import com.justdo.plug.blog.domain.blog.dto.BlogResponse.BlogProc;
import com.justdo.plug.blog.domain.blog.dto.BlogResponse.ImageResult;
import com.justdo.plug.blog.domain.blog.dto.BlogResponse.MyBlogResult;
import com.justdo.plug.blog.domain.blog.service.BlogCommandService;
import com.justdo.plug.blog.domain.blog.service.BlogQueryService;
import com.justdo.plug.blog.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Blog API")
@RestController
@RequestMapping("/blogs")
@RequiredArgsConstructor
public class BlogController {

    private final BlogCommandService blogCommandService;
    private final BlogQueryService blogQueryService;

    @Operation(summary = "회원 가입 - 회원 가입 시 자동으로 블로그 생성을 요청합니다.", description = "Open feign 요청 / 사용자 회원가입 시 자동으로 하나의 블로그가 생성됩니다.")
    @PostMapping
    public ApiResponse<BlogProc> createBlog(@RequestParam Long memberId) {

        return ApiResponse.onSuccess(blogCommandService.createBlog(memberId));
    }

    @Operation(summary = "블로그 페이지 - 블로그 페이지를 조회합니다.", description = "Blog 정보, 최신 4개의 Post, MemberName을 반환합니다.")
    @GetMapping("/{blogId}")
    public ApiResponse<BlogPage> getBlogPage(@PathVariable Long blogId) {

        return ApiResponse.onSuccess(blogQueryService.findBlogPage(blogId));
    }

    @Operation(summary = "마이 페이지 - 사용자 및 블로그 정보를 조회합니다.", description = "마이페이지에 보여줄 사용자 및 블로그 개인 정보를 조회합니다.")
    @Parameter(name = "blogId", description = "블로그 Id, Path Variable입니다.", required = true, example = "1", in = ParameterIn.PATH)
    @GetMapping("/mypage/{blogId}")
    public ApiResponse<MyBlogResult> myBlog(@PathVariable(name = "blogId") Long blogId) {
        return ApiResponse.onSuccess(blogQueryService.getBlogInfo(blogId));
    }

    @Operation(summary = "마이 페이지 - 사용자 및 블로그 정보를 수정합니다.", description = "마이페이지에서 사용자 및 블로그 정보를 수정합니다.")
    @Parameter(name = "blogId", description = "블로그 Id, Path Variable입니다.", required = true, example = "1", in = ParameterIn.PATH)
    @PatchMapping("/mypage/{blogId}")
    public ApiResponse<BlogProc> updateBlog(@RequestBody BlogRequest request,
            @PathVariable(name = "blogId") Long blogId) {

        return ApiResponse.onSuccess(blogCommandService.updateBlog(request, blogId));
    }

    @Operation(summary = "이미지 Upload API - Glue Service에서 사용하는 이미지 업로드 API 입니다.", description = "Blog, Post, Member 서버에서 이미지 업로드 시 사용합니다.")
    @PostMapping(value = "/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<ImageResult> uploadImage(
            @Parameter(name = "multipartFile", description = "multipart/form-data 형식의 이미지를 전달받습니다.")
            @RequestPart(name = "multipartFile") MultipartFile multipartFile) {
        return ApiResponse.onSuccess(blogCommandService.imageUpload(multipartFile));
    }

    @Operation(summary = "검색 페이지 - 게시글 검색 후 '블로그 보기'를 요청합니다.", description = "검색한 게시글의 블로그 정보를 조회합니다.")
    @Parameter(name = "page", description = "페이징 번호", example = "0", in = ParameterIn.QUERY)
    @PostMapping("/search")
    public BlogInfoList searchBlog(@RequestBody List<Long> blogIdList,
            @RequestParam(value = "page", defaultValue = "0") int page) {

        return blogQueryService.searchBlogs(blogIdList, page);
    }

    @Operation(summary = "댓글 페이지 - 댓글에 작성된 블로그의 정보를 조회합니다.", description = "Open feign 요청 - 댓글에 작성된 블로그의 정보를 전달합니다.")
    @PostMapping("/comments")
    public List<BlogComment> findBlogInfoToComment(@RequestBody List<Long> memberIdList) {

        return blogQueryService.findBlogComments(memberIdList);
    }

}
