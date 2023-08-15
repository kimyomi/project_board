package com.board.facamboard.dto.response;

import com.board.facamboard.dto.ArticleWithCommentsDto;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;


@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class ArticleWithCommentResponse implements Serializable {

    private Long id;
    private String title;
    private String content;
    private String hashtag;
    private LocalDateTime createdAt;
    private String email;
    private String nickname;
    private Set<ArticleCommentResponse> articleCommentResponses;

    public static ArticleWithCommentResponse of(Long id, String title, String content, String hashtag, LocalDateTime createdAt, String email, String nickname, Set<ArticleCommentResponse> articleCommentResponses) {
        return new ArticleWithCommentResponse(id, title, content, hashtag, createdAt, email, nickname, articleCommentResponses);
    }

    public static ArticleWithCommentResponse from(ArticleWithCommentsDto dto) {
        String nickname = dto.getUserAccountDto().getNickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = dto.getUserAccountDto().getUserId();
        }

        return new ArticleWithCommentResponse(
                dto.getId(),
                dto.getTitle(),
                dto.getContent(),
                dto.getHashtag(),
                dto.getCreatedAt(),
                dto.getUserAccountDto().getEmail(),
                nickname,
                dto.getArticleCommentDtos().stream()
                        .map(ArticleCommentResponse::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new))
        );
    }

}
