package com.board.facamboard.domain;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

// 공통적으로 사용하고 싶은 필드가 있을 때 쓰는 어노테이션
@MappedSuperclass
@ToString
@Getter
@EntityListeners(AuditingEntityListener.class)
public class AuditingFields {
    // 메타 데이터
    // 초기에 설정하고 업데이트 안하는 경우 , updatable = false
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) // 날짜 파싱
    @CreatedDate
    @Column(nullable = false, updatable = false) private LocalDateTime createdAt; // 생성일시
    @CreatedBy
    @Column(nullable = false, length = 100, updatable = false) private String createdBy; // 생성자
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) // 날짜 파싱
    @LastModifiedDate
    @Column(nullable = false) private LocalDateTime modifiedAt; // 수정일시
    @LastModifiedBy
    @Column(nullable = false, length = 100) private String modifiedBy; // 수정자
}
