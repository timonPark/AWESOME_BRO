package org.awesomeboro.awesome_bro.article;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.awesomeboro.awesome_bro.common.BaseEntity;
import org.awesomeboro.awesome_bro.dto.article.ArticleCreateOrUpdateRequestDto;
import org.awesomeboro.awesome_bro.user.User;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Article extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false)
    private String title;
    @Column(length = 10000, nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Article(ArticleCreateOrUpdateRequestDto articleCreateRequestDto) {
        this.title = articleCreateRequestDto.getTitle();
        this.content = articleCreateRequestDto.getContent();
        this.user = articleCreateRequestDto.getUser();
    }

    public void updateArticleInfo(ArticleCreateOrUpdateRequestDto article) {
        this.title = article.getTitle();
        this.content =article.getContent();
    }

}
