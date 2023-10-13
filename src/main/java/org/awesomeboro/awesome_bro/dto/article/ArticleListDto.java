package org.awesomeboro.awesome_bro.dto.article;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.awesomeboro.awesome_bro.article.Article;
import org.awesomeboro.awesome_bro.common.BaseEntity;
import org.awesomeboro.awesome_bro.dto.user.UserInfoDto;

import java.time.LocalDateTime;
import java.util.List;

import static org.awesomeboro.awesome_bro.dto.user.UserInfoDto.convertToUserInfoDto;

@Getter
@RequiredArgsConstructor
public class ArticleListDto {
    private Long id;
    private String content;
    private UserInfoDto userInfo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;



    public ArticleListDto(Article article) {
        this.id = article.getId();
        this.content = article.getContent();
        this.userInfo =convertToUserInfoDto(article.getUser());
        this.createdAt = article.getCreatedAt();
        this.updatedAt = article.getUpdatedAt();
    }


}
