package com.mechoy.flowable;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Test {
    public static void main(String[] args) throws Exception {
        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
                .setJdbcUrl("jdbc:mysql://localhost:3306/flowable?useSSL=true")
                .setJdbcUsername("root")
                .setJdbcPassword("root")
                .setJdbcDriver("com.mysql.jdbc.Driver")
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

        ProcessEngine processEngine = cfg.buildProcessEngine();

        String path = "src/main/resources/activiti/classPath.bpmn20.xml"; // 替换为你的文件路径

        String content = new String(Files.readAllBytes(Paths.get(path)));
//        System.out.println(content);

        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment()
                .addString("xxxbpmn20.xml",content)
                .deploy();
    }
}
