package org.awesomeboro.awesome_bro.article;

import org.awesomeboro.awesome_bro.auth.CustomUserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ArticleService {

    public void createArticle(CustomUserDetails user) {
        System.out.println("유저의권한:"+user.getAuthorities());
    }
}
