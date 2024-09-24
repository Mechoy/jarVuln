//package com.mechoy.flowable.config;
//
//import com.mechoy.flowable.vo.MyBeans;
//import org.flowable.spring.SpringExpressionManager;
//import org.flowable.spring.SpringProcessEngineConfiguration;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.expression.spel.support.StandardEvaluationContext;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//public class FlowableConfiguration {
//
//    @Autowired
//    private ApplicationContext applicationContext;
//
//    @Bean
//    public SpringProcessEngineConfiguration processEngineConfiguration() {
//        SpringProcessEngineConfiguration config = new SpringProcessEngineConfiguration();
//        config.setExpressionManager(customExpressionManager());
//
//        // 其他配置项...
//        return config;
//    }
//
//    @Bean
//    public SpringExpressionManager customExpressionManager() {
//        Map<String, Object> beans = new HashMap<>();
//        beans.put("myService", new MyBeans());
//
//        StandardEvaluationContext context = new StandardEvaluationContext();
//        context.setVariables(beans);
//
//        return new SpringExpressionManager(applicationContext, context);
//    }
//}
