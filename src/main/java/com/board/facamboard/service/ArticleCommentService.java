package com.board.facamboard.service;

import com.board.facamboard.domain.type.SearchType;
import com.board.facamboard.dto.ArticleCommentDto;
import com.board.facamboard.dto.ArticleDto;
import com.board.facamboard.repository.ArticleCommentRepository;
import com.board.facamboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
//import javax.transaction.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class ArticleCommentService {
    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    @Transactional(readOnly = true)
    public List<ArticleCommentDto> searchArticleComments(Long articleId){
        return List.of();
    }


    public void saveArticleComment(ArticleCommentDto dto){

    }

    public void updateArticleComment(ArticleCommentDto dto){

    }

    public void deleteArticleComment(long articleCommentId){

    }

}
