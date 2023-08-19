package com.board.facamboard.repository;

import com.board.facamboard.domain.Article;
import com.board.facamboard.domain.QArticle;
import com.board.facamboard.domain.QArticleComment;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArticleRepository extends JpaRepository<Article, Long>,
        QuerydslPredicateExecutor<Article> // 검색 기능
        ,QuerydslBinderCustomizer<QArticle>
{

    // 검색어로 게시글을 검색하면 페이지 정보도 함께 반환함
    Page<Article> findByTitleContaining(String title, Pageable pageable);
    Page<Article> findByContentContaining(String content, Pageable pageable);
    Page<Article> findByUserAccount_UserIdContaining(String userId, Pageable pageable);
    Page<Article> findByUserAccount_NicknameContaining(String nickname, Pageable pageable);
    Page<Article> findByHashtag(String hashtag, Pageable pageable);

    // 검색에 대한 세부적인 규칙이 재구성됨
    @Override
    default void customize(QuerydslBindings bindings, QArticle root){
        // 구현은 안하고 jpa 가져다 사용할 예정
        // 검색 기능 구현 대상
        // listing 하지 않은 프로퍼티는 검색에서 제외시킴
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.title, root.content, root.hashtag, root.createdAt, root.createdBy);
        // bindings.bind(root.title).first((path, value) -> path.eq(value));
        // bindings.bind(root.title).first(SimpleExpression::eq);
        // bindings.bind(root.title).first(StringExpression::likeIgnoreCase); // like '${v}'
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase); // like '%${v}%'
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase); // like '%${v}%'
        bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase); // like '%${v}%'
        bindings.bind(root.createdAt).first(DateTimeExpression::eq); // 나중에 처리
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase); // like '%${v}%'
        // first() 첫번째 파라미터만 받겠다;
    }
}