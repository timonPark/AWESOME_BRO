package org.awesomeboro.awesome_bro.article;

import lombok.RequiredArgsConstructor;
import org.awesomeboro.awesome_bro.auth.CustomUserDetails;
import org.awesomeboro.awesome_bro.dto.article.ArticleCreateOrUpdateRequestDto;
import org.awesomeboro.awesome_bro.dto.article.ArticleFindOneDto;
import org.awesomeboro.awesome_bro.dto.article.ArticleListDto;
import org.awesomeboro.awesome_bro.user.User;
import org.awesomeboro.awesome_bro.user.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    @Transactional
    public String createArticle(ArticleCreateOrUpdateRequestDto articleCreateRequestDto, CustomUserDetails user) {
        User userInfo = userRepository.findById(user.getId()).orElseThrow();
        articleCreateRequestDto.setUser(userInfo);
        Article article = new Article(articleCreateRequestDto);
        articleRepository.save(article);
        return "게시글이 등록되었습니다.";
    }

    public List<ArticleListDto> articleList() {
        Sort sort = Sort.by(Sort.Direction.DESC,"id");
        List<Article> articleList = articleRepository.findAllByUseYn("y",sort);
        return articleList.stream().map(ArticleListDto::new).toList();
    }

    public ArticleFindOneDto findArticle(Long id) {
        Article article = articleRepository.findById(id).orElseThrow();
        return new ArticleFindOneDto(article);
    }

    @Transactional
    public ArticleFindOneDto updateArticle(Long id, ArticleCreateOrUpdateRequestDto updateArticle, CustomUserDetails currentUser) {
        Article article = articleRepository.findById(id).orElseThrow();
        if(!article.getUser().getId().equals(currentUser.getId())){
            throw new RuntimeException("해당 게시글을 수정할 권한이 없습니다.");
        }
        article.updateArticleInfo(updateArticle);
        return new ArticleFindOneDto(article);
    }

    @Transactional
    public String deleteArticle(Long id,CustomUserDetails currentUser) {
        Article article = articleRepository.findById(id).orElseThrow();
        if(!article.getUser().getId().equals(currentUser.getId())){
            throw new RuntimeException("해당 게시글을 삭제할 권한이 없습니다.");
        }
        article.softDelete();
        return "게시글이 삭제되었습니다.";
    }
}
