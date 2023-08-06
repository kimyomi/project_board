package com.board.facamboard.repository;

import com.board.facamboard.config.JpaConfig;
import com.board.facamboard.domain.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

//import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

// JpaConfig에서 자동으로 Auditing 해주는 기는 적용 → import 필요함
@DisplayName("Jpa연결테스트")
@Import(JpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {
    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    // 생성자 주입 패턴
    public JpaRepositoryTest(@Autowired ArticleRepository articleRepository, @Autowired ArticleCommentRepository articleCommentRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }


    @DisplayName("Select 테스트")
    @Test
    void givenTestData_whenSelecting_thenWorksFine(){
        // Given
        // When
        List<Article> articles = articleRepository.findAll();
        // Then
        assertThat(articles)
                .isNotNull()
                .hasSize(150); // 현재 개수 0개라서
    }


    @DisplayName("Insert 테스트")
    @Test
    void givenTestData_whenInserting_thenWorksFine(){
        // Given
        long previousCount = articleRepository.count(); // 기존 테이블 데이터 개수
        Article article = Article.of("new article","new content", "#spring");

        // When
        // save 한 내용을 return 받을 수 있음
        Article savedArticle = articleRepository.save(article);
        // Then
        assertThat(articleRepository.count()).isEqualTo(previousCount + 1);
    }

    @DisplayName("Update 테스트")
    @Test
    void givenTestData_whenUpdating_thenWorksFine(){
        // Given
        // long type이라서 L
        Article article = articleRepository.findById(1L).orElseThrow(); // 없을 때 throw
        String newHashTag = "#Spring";
        article.setHashtag(newHashTag);

        // When
        // save 한 내용을 return 받을 수 있음
        //Article savedArticle = articleRepository.save(article); // update도 save() 사용함
        // @Test 기본적으로 트랜잭션 rollback 기본 → rollback이 기본이라 어차피 반영 안되서 실행 안될 수 있음 → 로그에서 쿼리를 어떻게 볼지?
        Article savedArticle = articleRepository.saveAndFlush(article); // update도 save() 사용함
        //articleRepository.flush(); Flush를 해준다.
        // Then
        // 특정 컬럼에 특정 값이 들어가 있는지 확인함
        assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag",newHashTag);
    }


    @DisplayName("Delete 테스트")
    @Test
    void givenTestData_whenDeleting_thenWorksFine(){
        // Given
        Article article = articleRepository.findById(1L).orElseThrow(); // 없을 때 throw
        long previousArticleCnt = articleRepository.count();
        long previousArticleCommentCnt = articleCommentRepository.count(); // 댓글 전부 사라지도록 cascade 설정 적용했음
        // article에 연결된 comment가 몇개인지 확인
        // Size 반환 int type
        int deletedCommentSize = article.getArticleComments().size(); // comment 리스트 사이즈 가져옴

        articleRepository.delete(article);
        // When
        // Then
        // 특정 컬럼에 특정 값이 들어가 있는지 확인함
        assertThat(articleRepository.count()).isEqualTo(previousArticleCnt - 1);
        // 전체 comment 개수에서 지워져야할 comment 개수 빼서 데이터 확인
        assertThat(articleCommentRepository.count()).isEqualTo(previousArticleCommentCnt - deletedCommentSize);
    }
}