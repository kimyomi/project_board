package com.board.facamboard.service;

import com.board.facamboard.domain.type.SearchType;
import com.board.facamboard.dto.ArticleDto;
import com.board.facamboard.dto.ArticleWithCommentsDto;
import com.board.facamboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // 이걸로 가져옴

import java.util.List;
//import javax.transaction.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {
    private final ArticleRepository articleRepository;

    // page에 관한 정보를 내려줌
    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable){
//        return List.of(
//                ArticleDto.of(LocalDateTime.now(),"master","제목","본문글","spring boot")
//        );
        return Page.empty();
    }

    @Transactional(readOnly = true)
    public ArticleWithCommentsDto getArticle(Long articleId){
        return null;
    }


    public void saveArticle(ArticleDto dto){

    }

    public void updateArticle(ArticleDto dto){
    }

    public void deleteArticle(Long articleId){
    }

}
