//package com.mechoy.flowable.config;
//
//import com.mechoy.flowable.vo.MyBeans;
//import org.springframework.context.annotation.Configuration;
//import org.flowable.engine.ProcessEngine;
//import org.flowable.spring.SpringProcessEngineConfiguration;
//import org.springframework.context.annotation.Bean;
//import javax.sql.DataSource;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import java.util.HashMap;
//
//@Configuration
//public class FlowableBeanConfig {
//    private final DataSource dataSource;
//    private final PlatformTransactionManager transactionManager;
//
//    private final MyBeans myBeans; // 注入需要暴露的bean
//
//    public FlowableBeanConfig(DataSource dataSource, PlatformTransactionManager transactionManager, MyBeans myBeans) {
//        this.dataSource = dataSource;
//        this.transactionManager = transactionManager;
//        this.myBeans = myBeans;
//    }
//
//    @Bean
//    public ProcessEngine processEngine() {
//        SpringProcessEngineConfiguration configuration = new SpringProcessEngineConfiguration();
//        configuration.setDataSource(dataSource);
//        configuration.setTransactionManager(transactionManager);
//
//        // 允许 BPMN 表达式访问所有的 Spring beans
//        HashMap<Object, Object> hashMap = new HashMap<>();
////        hashMap.put("myBeans",new MyBeans());
////        configuration.setBeans(hashMap); // 传入 null 表示默认暴露所有 beans
//
//        configuration.setBeans(hashMap);
//        return configuration.buildProcessEngine();
//    }
//}
