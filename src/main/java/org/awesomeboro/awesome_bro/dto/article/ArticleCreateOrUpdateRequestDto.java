package org.awesomeboro.awesome_bro.dto.article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.awesomeboro.awesome_bro.user.User;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArticleCreateOrUpdateRequestDto {

    private String title;
    private String content;
    private User user;
}
