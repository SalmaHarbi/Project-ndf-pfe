package com.entreprise.msexpense.batchConfig;


import com.entreprise.msexpense.entities.BatchResultStorage;
import com.entreprise.msexpense.entities.Depense;
import com.entreprise.msexpense.entities.RapportDepense;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.math.BigDecimal;

@Slf4j
@Configuration
public class BatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EntityManagerFactory entityManagerFactory;


    @Autowired
    private DataSource dataSource;

    public BatchConfig(JobRepository jobRepository,
                       PlatformTransactionManager transactionManager,
                       EntityManagerFactory entityManagerFactory) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.entityManagerFactory = entityManagerFactory;
    }

    @Bean
    public Job simpleJob(Step step) {
        return new JobBuilder("rapport", jobRepository)
                .start(step)
                .build();
    }

    @Bean
    public Step step(JpaPagingItemReader<Depense> reader,
                     ItemProcessor<Depense, Depense> processor,
                     ItemWriter<Depense> writer) {
        return new StepBuilder("Rapport", jobRepository)
                .<Depense, Depense>chunk(5, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .allowStartIfComplete(true)  // Permet de relancer même si COMPLETED
                .build();
    }



    @Bean
    public JpaPagingItemReader<Depense> reader(EntityManagerFactory entityManagerFactory) {
        JpaPagingItemReader<Depense> reader = new JpaPagingItemReader<>();
        reader.setName("Rapport");
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setQueryString("SELECT d FROM Depense d where statut=true");
        reader.setPageSize(10);
        reader.setSaveState(true);
        return reader;
    }


    @Bean
    public ItemProcessor<Depense, Depense> highAmountProcessor() {
        return depense -> {
            if (depense.getMontantconverti() != null && depense.getMontantconverti().compareTo(new BigDecimal("3000")) > 0) {
                return depense;
            }
            return null;
        };
    }




    @Bean
    public ItemWriter<Depense> writer(BatchResultStorage storage) {
        return new DatabaseItemWriter(storage);
    }

}