//package com.mechoy.flowable.config;
//
//import org.flowable.engine.ProcessEngine;
//import org.flowable.spring.SpringProcessEngineConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//import java.util.Collections;
//
//@Configuration
//public class FlowableConfig {
//
//    private final DataSource dataSource;
//    private final PlatformTransactionManager transactionManager;
//
//    public FlowableConfig(DataSource dataSource, PlatformTransactionManager transactionManager) {
//        this.dataSource = dataSource;
//        this.transactionManager = transactionManager;
//    }
//
//    @Bean
//    public ProcessEngine processEngine() {
//        SpringProcessEngineConfiguration configuration = new SpringProcessEngineConfiguration();
//        configuration.setDataSource(dataSource);
//        configuration.setTransactionManager(transactionManager);
//
//        // 设置 beans 参数为空，禁止 BPMN 表达式访问任何 Spring bean
//        configuration.setBeans(Collections.emptyMap());
//
//        return configuration.buildProcessEngine();
//    }
//}
