package com.mechoy.bpmn;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Process;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.util.io.InputStreamSource;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.rest.service.api.RestResponseFactory;
import org.activiti.rest.service.api.repository.ModelResponse;
import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;

import java.util.HashMap;
import org.activiti.bpmn.model.*;

public class BpmnTest {
    public static void main(String[] args) throws Exception {
//        testFilePath();

        realCase1();
//        ScriptEngineManager scriptEngineManager = (ScriptEngineManager)Class.forName("javax.script.ScriptEngineManager").newInstance();
//        scriptEngineManager.getEngineByName("js").eval("function test(){ return java.lang.Runtime};r=test();r.getRuntime().exec(\"calc\")");
    }

    public static void testFilePath() throws Exception {
        // 读取指定文件，并解析文件内容转为BpmnModel对象
        FileInputStream fileInputStream = new FileInputStream("src/main/resources/bpmn/bpmn1.bpmn20.xml");
        InputStreamSource source = new InputStreamSource(fileInputStream);
        BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(source, false, false, "UTF-8");
        // 设置主流程的id，这个ID可以被用来在以后检索或启动这个流程。此处setId的参数同样随意
        bpmnModel.getMainProcess().setId("111111");
//        bpmnModel.getMainProcess().setId(StringUtils.defaultIfEmpty("",bpmnModel.getMainProcess().getId()));
//        bpmnModel.getMainProcess().setId(StringUtils.defaultIfEmpty("",bpmnModel.getMainProcess().getName()));
        // 创建一个新的部署构建器并通过addBpmnModel将加载到内存中的BpmnModel添加到部署构建器中。
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        // 调用部署构建器的deploy方法将流程部署到Activiti引擎的存储库中，并返回一个表示部署结果的Deployment对象。
        Deployment deployment = repositoryService.createDeployment()
                .addBpmnModel("", bpmnModel)    // 此处的参数1随意,因为只是存储在数据库中的
                .deploy();

        // 此处指定的key需要与xml文件中的<process id="myProcess" isExecutable="true">的id对应上
        ProcessInstance processInstance =
                processEngine.getRuntimeService().startProcessInstanceByKey("myProcess");

        System.out.println("Deployment ID: " + deployment.getId());
    }

    public static void testClassPath() {
        // 创建流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        // 获取仓库服务
        RepositoryService repositoryService = processEngine.getRepositoryService();

        // 部署流程定义文件
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("bpmn/bpmn.bpmn20.xml")
                .deploy();

        // 验证已部署的流程定义
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployment.getId())
                .singleResult();

        // 启动流程实例
        ProcessInstance processInstance =
                processEngine.getRuntimeService().startProcessInstanceByKey("myProcess");

        System.out.println("部署的流程ID: " + processDefinition.getId());
    }

    /**
     * 这个是一个真实案例
     */
    public static void realCase1() throws Exception {

        // 创建流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        // 获取仓库服务
        RepositoryService repositoryService = processEngine.getRepositoryService();

        ObjectMapper objectMapper = new ObjectMapper();
        RestResponseFactory restResponseFactory = new RestResponseFactory();

        // 案例中分为两步，此处直接在一处中实现
        // 导入新的流程
        // 1. 创建inputStreamSource用于构建BpmnModel对象
        FileInputStream fileInputStream = new FileInputStream("src/main/resources/bpmn/xxx.bpmn20.xml");
        InputStreamSource source = new InputStreamSource(fileInputStream);

        // 2.构建BpmnModel对象
        BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(source, false, false, "UTF-8");
        // 2.1 真实案例时，需要传参，此处直接代替
        bpmnModel.getMainProcess().setId(bpmnModel.getMainProcess().getId());
        bpmnModel.getMainProcess().setName(bpmnModel.getMainProcess().getName());

        // 3 创建Model对象
        Model model = repositoryService.newModel();
        // 3.1 设置Model对象相关值
        model.setCategory("default");
        model.setDeploymentId(null);
        model.setKey(bpmnModel.getMainProcess().getId());
        model.setName(bpmnModel.getMainProcess().getName());

        String metaInfo = model.getMetaInfo();
        if (StringUtils.isEmpty(metaInfo)) {
            System.out.println("metaInfo is null");
            HashMap<String, String> map = new HashMap<>();
            map.put("name", model.getName());
            map.put("version", String.valueOf(model.getVersion()));
            metaInfo = objectMapper.writeValueAsString(map);
        }
        model.setMetaInfo(metaInfo);

        repositoryService.saveModel(model);

        ObjectNode content = new BpmnJsonConverter().convertToJson(bpmnModel);
        repositoryService.addModelEditorSource(model.getId(), objectMapper.writeValueAsBytes(content));

        System.out.println("model id:" + model.getId());
        System.out.println("model name: " + model.getName());
        System.out.println("bpmnModel id:" + bpmnModel.getMainProcess().getId());
        System.out.println("bpmnModel name:" + bpmnModel.getMainProcess().getName());

        String id = model.getId();


        Model modelForData = repositoryService.getModel(id);
        byte[] modelEditorSource = repositoryService.getModelEditorSource(id);
        JsonNode jsonNode = objectMapper.readTree(modelEditorSource);
        BpmnModel bpmnModelForData = new BpmnJsonConverter().convertToBpmnModel(jsonNode);
        Deployment deploy = repositoryService.createDeployment().category(modelForData.getCategory()).name(modelForData.getName())
                .key(modelForData.getKey()).addBpmnModel(modelForData.getKey() + "bpmn20.xml", bpmnModelForData).deploy();
        modelForData.setDeploymentId(deploy.getId());
        repositoryService.saveModel(modelForData);
    }
}
