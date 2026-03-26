package kr.co.secondProject.attendance.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;


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