package org.awesomeboro.awesome_bro.article;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findAllByUseYn(String useYn, Sort sort);
}
