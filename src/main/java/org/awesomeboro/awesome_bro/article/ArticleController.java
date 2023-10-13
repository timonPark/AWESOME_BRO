package org.awesomeboro.awesome_bro.article;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.awesomeboro.awesome_bro.auth.CustomUserDetails;
import org.awesomeboro.awesome_bro.controller.ApiResponse;
import org.awesomeboro.awesome_bro.dto.article.ArticleCreateOrUpdateRequestDto;
import org.awesomeboro.awesome_bro.dto.article.ArticleFindOneDto;
import org.awesomeboro.awesome_bro.dto.article.ArticleListDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.awesomeboro.awesome_bro.controller.ApiResponse.createSuccess;

@RestController
@RequiredArgsConstructor
@RequestMapping("/article")
@Slf4j
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping("/create")
    public ApiResponse<String> createArticle(@AuthenticationPrincipal CustomUserDetails user, @RequestBody ArticleCreateOrUpdateRequestDto articleCreateRequestDto) {
        return createSuccess(articleService.createArticle(articleCreateRequestDto,user));
    }

    @GetMapping("/list")
    public ApiResponse<List<ArticleListDto>> ArticleList() {
        return createSuccess(articleService.articleList());
    }
    @GetMapping("/{id}")
    public ApiResponse<ArticleFindOneDto> Article(@PathVariable Long id) {
        return createSuccess(articleService.findArticle(id));
    }

    @PatchMapping("/update/{id}")
    public ApiResponse<ArticleFindOneDto> updateArticle(@PathVariable Long id,
                                                        @RequestBody ArticleCreateOrUpdateRequestDto article,
                                                        @AuthenticationPrincipal CustomUserDetails currentUser) {
        return createSuccess(articleService.updateArticle(id,article,currentUser));
    }

    @PatchMapping("/delete/{id}")
    public ApiResponse<String> deleteArticle(@PathVariable Long id,
                                             @AuthenticationPrincipal CustomUserDetails currentUser) {
        return createSuccess(articleService.deleteArticle(id,currentUser));
    }
}
