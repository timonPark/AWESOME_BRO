package org.awesomeboro.awesome_bro.article;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.awesomeboro.awesome_bro.auth.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/article")
@Slf4j
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping("/create")
    public void createArticle(@AuthenticationPrincipal CustomUserDetails user) {
        log.info("이메일 : "+user.getEmail()+" id : "+ user.getAuthorities());
        articleService.createArticle(user);

    }
}
