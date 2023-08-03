package com.board.facamboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class JpaConfig {

    // 작성자 자동 할당
    @Bean
    public AuditorAware<String> auditorAware(){
        return () -> Optional.of("yomim"); // TODO 스프링 시큐리티로 인증가능 붙이고 수정
    }
}
