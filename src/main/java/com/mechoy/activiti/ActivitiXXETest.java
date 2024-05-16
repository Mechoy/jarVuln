package com.mechoy.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ActivitiXXETest {
    public static void main(String[] args) throws Exception {
        String xml = new String(Files.readAllBytes(Paths.get("src/main/resources/activiti/xxe.bpmn20.xml")), StandardCharsets.UTF_8);
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();

        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
        deploymentBuilder.addString("xxe.bpmn20.xml", xml);
        Deployment deploy = deploymentBuilder.deploy();
    }
}
