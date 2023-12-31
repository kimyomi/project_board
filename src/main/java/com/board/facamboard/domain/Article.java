package com.board.facamboard.domain;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString(callSuper = true) // 안쪽 auditing 까지 들어가서 찍어주겠다
@Table(indexes = {
        @Index(columnList = "title"), // comment는 title 없고 본문 검색으로 index 주기
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy"),
})
@Entity
public class Article extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter @ManyToOne(optional = false) private UserAccount userAccount; // 유저 정보 (ID)

    @Setter @Column(nullable = false) private String title; // 제목

    @Setter @Column(nullable = false, length = 10000)private String content; // 본문

    @Setter private String hashtag; // 해시태그
    
    // 양방향 바인딩
    // 중복 허용 X
    // 한번만 세팅
    // 두개 테이블 이름 합쳐서 새 테이블 만드느니, mappedBy 설정해줌
    // 실무에서는 cascade 등 양방향 바인딩 (참조 관계) 안하는 경우 많음
    // 강한 데이터 결합으로 데이터 처리 번거로움. 의도된 대로 데이터 운영 관리 불가능할 수 있음
    // 순환 참조 막으려면 Article에서 tostring 끊어줘야함.
    // article > articlecomment >> / article
    @ToString.Exclude @OrderBy("createdAt DESC") @OneToMany(mappedBy = "article", cascade = CascadeType.ALL) // 공부 목적
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

    // protected 사용해서 밖에서 New 사용하지 못하도록 함
    protected Article(){}

    //setter 로 접근하도록
    private Article(UserAccount userAccount, String title, String content, String hashtag) {
        this.userAccount = userAccount;
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static Article of(UserAccount userAccount, String title, String content, String hashtag) {
        return new Article(userAccount, title, content, hashtag);
    }

    //equalsandhashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return id != null && id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
