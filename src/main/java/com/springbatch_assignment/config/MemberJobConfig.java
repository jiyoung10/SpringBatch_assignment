package com.springbatch_assignment.config;

import com.springbatch_assignment.domain.entity.Member;
import com.springbatch_assignment.domain.entity.MemberMonthlyWork;
import com.springbatch_assignment.service.BusinessService;
import com.springbatch_assignment.service.VacationService;
import com.springbatch_assignment.service.WorkOutsideService;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class MemberJobConfig {

    private final ApplicationContext applicationContext;

    @Qualifier("entityManagerFactory")
    private final EntityManagerFactory entityManagerFactory;

    private final BusinessService businessService;
    private final VacationService vacationService;
    private final WorkOutsideService workOutsideService;

    @Value("${batch.chunk-size:3}")
    private int chunkSize;

    @Bean("memberWorkJob")
    Job memberWorkJob(JobRepository jobRepository, Step memberWorkStep) {
        return new JobBuilder("memberWorkJob", jobRepository)
                .start(memberWorkStep)
                .build();
    }

    @Bean
    @JobScope
    Step memberWorkStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("memberWorkStep", jobRepository)
                .<Member, MemberMonthlyWork>chunk(chunkSize, transactionManager)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    @Bean
    @StepScope
    JpaPagingItemReader<Member> itemReader() {
        return new JpaPagingItemReaderBuilder<Member>()
                .name("memberItemReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(chunkSize)  // 한 번에 읽어올 데이터의 수를 설정
                .queryString("SELECT m FROM Member m")
                .build();
    }

    @Bean
    @StepScope
    ItemProcessor<Member, MemberMonthlyWork> itemProcessor() {
        return member -> {
            LocalDate startDate = LocalDate.now().withDayOfMonth(1);
            LocalDate endDate = startDate.plusMonths(1).minusDays(1);

            Integer workHours = businessService.getWorkHour(member.getId(), startDate, endDate);
            Integer vacationHours = vacationService.getWorkHour(member.getId(), startDate, endDate);
            Integer outsideHours = workOutsideService.getHour(member.getId(), startDate, endDate);

            int totalHours = workHours + vacationHours + outsideHours;
            log.info("totalHours: {}", totalHours);

            return MemberMonthlyWork.builder()
                    .yearMonth(startDate.format(DateTimeFormatter.ofPattern("yyyyMM")))
                    .memberId(member.getId())
                    .hour(totalHours)
                    .creationDate(new Date())
                    .build();
        };
    }

    @Bean
    @StepScope
    JpaItemWriter<MemberMonthlyWork> itemWriter() {
        JpaItemWriter<MemberMonthlyWork> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }
}

