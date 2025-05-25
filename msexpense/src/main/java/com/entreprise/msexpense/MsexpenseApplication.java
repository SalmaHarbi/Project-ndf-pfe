package com.entreprise.msexpense;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@EnableConfigurationProperties
@RefreshScope
public class MsexpenseApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsexpenseApplication.class, args);
    }

    @Bean
    CommandLineRunner runJob(JobLauncher jobLauncher, Job simpleJob) {
        return args -> {
            jobLauncher.run(simpleJob, new org.springframework.batch.core.JobParameters());
        };
    }

}
