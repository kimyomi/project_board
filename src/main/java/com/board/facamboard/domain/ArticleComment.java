package com.board.facamboard.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "content"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy"),
})
@EntityListeners(AuditingEntityListener.class)
@Entity
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Id로 연결하지 않고, 객체 지향 관점으로 Article entity 사용함
    // ManyToOne 연관 관계를 매핑할 때 사용함
    // 앞이 내 기준
    // cascade none 기본 (댓글 지워도 본 게시글 지워지면 안됨)
    @Setter @ManyToOne(optional = false) // optional 하지 않다 = 필수 값이다.
    private Article article; // 게시글 (ID)
    @Setter
    @Column(nullable = false, length = 500)private String content; // 본문

    @CreatedDate
    @Column(nullable = false) private LocalDateTime createdAt; // 생성일시
    @CreatedBy
    @Column(nullable = false, length = 100) private String createdBy; // 생성자
    @LastModifiedDate
    @Column(nullable = false) private LocalDateTime modifiedAt; // 수정일시
    @LastModifiedBy
    @Column(nullable = false, length = 100) private String modifiedBy; // 수정자

    public ArticleComment() {
    }

    private ArticleComment(Article article, String content) {
        this.article = article;
        this.content = content;
    }

    private ArticleComment of(Article article, String content) {
        return new ArticleComment(article,content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleComment that = (ArticleComment) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
