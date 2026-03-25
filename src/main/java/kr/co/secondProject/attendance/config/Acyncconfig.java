package kr.co.secondProject.attendance.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 비동기(@Async) 처리 설정 클래스
 *
 * ※ 추가 어노테이션 설명:
 *  - @EnableAsync : 스프링의 @Async 기능을 활성화
 *                   이 어노테이션이 없으면 @Async가 무시되고 동기 방식으로 실행됨
 */
@Configuration
@EnableAsync
public class Acyncconfig {

    @Bean(name = "attendanceTaskExecutor")
    public Executor attendanceTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5); // 기본 유지 스레드 수 
        executor.setMaxPoolSize(10); // 최대 스레드 수
        executor.setQueueCapacity(50); // 대기 큐 용량
        executor.setThreadNamePrefix("Attendance-Async-");
        executor.initialize();
        return executor;
    }
}