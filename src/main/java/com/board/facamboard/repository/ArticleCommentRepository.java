package com.board.facamboard.repository;

import com.board.facamboard.domain.ArticleComment;
import com.board.facamboard.domain.QArticle;
import com.board.facamboard.domain.QArticleComment;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArticleCommentRepository extends
        JpaRepository<ArticleComment, Long>,
        QuerydslPredicateExecutor<ArticleComment> // 검색 기능
        , QuerydslBinderCustomizer<QArticleComment>
{
    @Override
    default void customize(QuerydslBindings bindings, QArticleComment root){
        // 구현은 안하고 jpa 가져다 사용할 예정

        // listing 하지 않은 프로퍼티는 검색에서 제외시킴
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.content, root.createdAt, root.createdBy);
        // bindings.bind(root.title).first((path, value) -> path.eq(value));
        // bindings.bind(root.title).first(SimpleExpression::eq);
        // bindings.bind(root.title).first(StringExpression::likeIgnoreCase); // like '${v}'
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase); // like '%${v}%'
        bindings.bind(root.createdAt).first(DateTimeExpression::eq); // 나중에 처리
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase); // like '%${v}%'
        // first() 첫번째 파라미터만 받겠다;
    }
}