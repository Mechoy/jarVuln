package com.mechoy.bpmn;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.util.io.InputStreamSource;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.StringUtils;

import javax.script.ScriptEngineManager;
import java.io.FileInputStream;

public class BpmnTest {
    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("bpmn1.bpmn20.xml");
        InputStreamSource source = new InputStreamSource(fileInputStream);
        BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(source, false, false, "UTF-8");
        bpmnModel.getMainProcess().setId(StringUtils.defaultIfEmpty("",bpmnModel.getMainProcess().getId()));
        bpmnModel.getMainProcess().setId(StringUtils.defaultIfEmpty("name",bpmnModel.getMainProcess().getName()));
//
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment()
                .addBpmnModel("bpmn1.bpmn20.xml", bpmnModel)
                .deploy();

//        ProcessInstance processInstance =
//                processEngine.getRuntimeService().startProcessInstanceByKey("myProcess");

//        System.out.println("Deployment ID: "+deployment.getId());

//        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        // 部署流程定义文件
//        processEngine.getRepositoryService().createDeployment()
//                .addClasspathResource("bpmn1.bpmn20.xml")
//                .deploy();

        // 启动流程实例
        ProcessInstance processInstance =
                processEngine.getRuntimeService().startProcessInstanceByKey("myProcess");

        System.out.println("启动的流程实例ID: " + processInstance.getId());

//        ScriptEngineManager scriptEngineManager = (ScriptEngineManager)Class.forName("javax.script.ScriptEngineManager").newInstance();
//        scriptEngineManager.getEngineByName("js").eval("function test(){ return java.lang.Runtime};r=test();r.getRuntime().exec(\"calc\")");


    }
}
