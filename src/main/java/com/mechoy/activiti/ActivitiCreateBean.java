package com.mechoy.activiti;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.util.io.InputStreamSource;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;

public class ActivitiCreateBean {
    /**
     * 测试使用表达式构造一个javaBean,暂未测试成功
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // 读取指定文件，并解析文件内容转为BpmnModel对象
        FileInputStream fileInputStream = new FileInputStream("src/main/resources/bpmn/bean.bpmn20.xml");
        InputStreamSource source = new InputStreamSource(fileInputStream);
        BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(source, false, false, "UTF-8");

        // 设置主流程的id，这个ID可以被用来在以后检索或启动这个流程。此处setId的参数同样随意
        // 同样，此处设置的ID，也是processEngine.getRuntimeService().startProcessInstanceByKey()中的key
        bpmnModel.getMainProcess().setId(StringUtils.defaultIfEmpty("", bpmnModel.getMainProcess().getId()));
        bpmnModel.getMainProcess().setName(StringUtils.defaultIfEmpty("", bpmnModel.getMainProcess().getName()));

        // 创建一个新的部署构建器并通过addBpmnModel将加载到内存中的BpmnModel添加到部署构建器中。
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        // 调用部署构建器的deploy方法将流程部署到Activiti引擎的存储库中，并返回一个表示部署结果的Deployment对象。
        Deployment deployment = repositoryService.createDeployment()
                .addBpmnModel("myProcess.bpmn20.xml", bpmnModel)
                .deploy();

        // 此处指定的key需要与xml文件中的<process id="myProcess" isExecutable="true">的id对应上
        // 当上面设置bpmnModel.getMainProcess().setId()之后，要与此处设置的值相同
        ProcessInstance processInstance =
                processEngine.getRuntimeService().startProcessInstanceByKey("bpmn1");

        System.out.println("Deployment ID: " + deployment.getId());

    }
}
