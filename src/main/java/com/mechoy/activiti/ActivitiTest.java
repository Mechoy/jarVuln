package com.mechoy.activiti;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.util.io.InputStreamSource;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.Model;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.rest.service.api.RestResponseFactory;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class ActivitiTest {
    public static void main(String[] args) throws Exception {
//        ScriptEngineManager scriptEngineManager = (ScriptEngineManager)Class.forName("javax.script.ScriptEngineManager").newInstance();
//        scriptEngineManager.getEngineByName("js").eval("function test(){ return java.lang.Runtime};r=test();r.getRuntime().exec(\"calc\")");
    }

    /**
     * 测试 deploymentBuilder.deploy() 是否会执行命令，会执行
     * @throws Exception
     */
    @Test
    public void testDeploymentBuilder() throws Exception {
        String xml = new String(Files.readAllBytes(Paths.get("src/main/resources/activiti/deploymentBuilder.bpmn20.xml")), StandardCharsets.UTF_8);
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();

        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
        deploymentBuilder.addString("realCase.bpmn20.xml", xml);
        Deployment deploy = deploymentBuilder.deploy();
    }


    /**
     * 测试传入文件部署流程，经测试，部署过程中并不会执行，需流程进行执行时才会
     * 测试通过，能够执行，但所因引用的XML中编写的内容导致，需要流程执行才会执行命令，若需要在部署时执行，则更换xml即可
     * @throws Exception
     */
    @Test
    public void testFilePath() throws Exception {
        // 读取指定文件，并解析文件内容转为BpmnModel对象
        FileInputStream fileInputStream = new FileInputStream("src/main/resources/activiti/filePath.bpmn20.xml");
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

    @Test
    public void test111() throws Exception {
        // 读取指定文件，并解析文件内容转为BpmnModel对象
//        FileInputStream fileInputStream = new FileInputStream("src/main/resources/activiti/test.bpmn20.xml");
//        InputStreamSource source = new InputStreamSource(fileInputStream);
//        BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(source, false, false, "UTF-8");



        // 创建一个新的部署构建器并通过addBpmnModel将加载到内存中的BpmnModel添加到部署构建器中。
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        // 部署BPMN文件
        Deployment deployment = processEngine.getRepositoryService().createDeployment()
                .addClasspathResource("activiti/test.bpmn20.xml")
                .deploy();

        // 启动流程实例
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("testProcess");
    }

    /**
     * 测试直接从classPath中拿文件进行部署，在部署过程中就会执行
     * 测试通过，能够执行
     */
    @Test
    public void testClassPath() {
        // 创建流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        // 获取仓库服务
        RepositoryService repositoryService = processEngine.getRepositoryService();

        // 部署流程定义文件
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("activiti/classPath.bpmn20.xml")
                .deploy();

        // 验证已部署的流程定义
//        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
//                .deploymentId(deployment.getId())
//                .singleResult();

        // 启动流程实例
//        ProcessInstance processInstance =
//                processEngine.getRuntimeService().startProcessInstanceByKey("myProcess");

//        System.out.println("部署的流程ID: " + processDefinition.getId());
    }

    /**
     * 这个是一个真实案例
     * 能够成功执行
     */
    @Test
    public void realCase1() throws Exception {

        // 创建流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        // 获取仓库服务
        RepositoryService repositoryService = processEngine.getRepositoryService();

        ObjectMapper objectMapper = new ObjectMapper();
        RestResponseFactory restResponseFactory = new RestResponseFactory();

        // 案例中分为两步，此处直接在一处中实现
        // 导入新的流程
        // 1.1. 创建inputStreamSource用于构建BpmnModel对象
        FileInputStream fileInputStream = new FileInputStream("src/main/resources/bpmn/realCase.bpmn20.xml");
        InputStreamSource source = new InputStreamSource(fileInputStream);

        // 1.2.构建BpmnModel对象
        BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(source, false, false, "UTF-8");
        // 1.2.1 真实案例时，需要传参，此处直接不传参了
        bpmnModel.getMainProcess().setId(bpmnModel.getMainProcess().getId());
        bpmnModel.getMainProcess().setName(bpmnModel.getMainProcess().getName());

        // 1.3 创建Model对象
        Model model = repositoryService.newModel();
        // 1.3.1 设置Model对象相关值
        model.setCategory("default");
        model.setDeploymentId(null);
        model.setKey(bpmnModel.getMainProcess().getId());
        model.setName(bpmnModel.getMainProcess().getName());
        // 1.3.2 检查Model对象的相关值
        String metaInfo = model.getMetaInfo();
        if (StringUtils.isEmpty(metaInfo)) {
            System.out.println("metaInfo is null");
            HashMap<String, String> map = new HashMap<>();
            map.put("name", model.getName());
            map.put("version", String.valueOf(model.getVersion()));
            metaInfo = objectMapper.writeValueAsString(map);
        }
        model.setMetaInfo(metaInfo);

        // 1.4. 保存model对象
        repositoryService.saveModel(model);

        // 1.5. 将xml转成json,然后再转成字节 然后再进行保存
        ObjectNode content = new BpmnJsonConverter().convertToJson(bpmnModel);
        repositoryService.addModelEditorSource(model.getId(), objectMapper.writeValueAsBytes(content));

        // 1.6 真实案例处是构建了一个Response返回，此处直接打印在控制台
        System.out.println("model id:" + model.getId());
        System.out.println("model name: " + model.getName());
        System.out.println("bpmnModel id:" + bpmnModel.getMainProcess().getId());
        System.out.println("bpmnModel name:" + bpmnModel.getMainProcess().getName());

        // 真实案例第二步
        // 2.1 根据modelId 拿出刚刚存入数据库中的ID
        String id = model.getId();

        Model modelForData = repositoryService.getModel(id);
        // 2.1.1 拿出存在数据库中的数据
        byte[] modelEditorSource = repositoryService.getModelEditorSource(id);
        JsonNode jsonNode = objectMapper.readTree(modelEditorSource);
        // 2.2 根据jsonNode转成BpmnModel对象
        BpmnModel bpmnModelForData = new BpmnJsonConverter().convertToBpmnModel(jsonNode);
        // 2.3 部署 在下一行即可实现命令执行
        Deployment deploy = repositoryService.createDeployment().category(modelForData.getCategory()).name(modelForData.getName())
                .key(modelForData.getKey()).addBpmnModel(modelForData.getKey() + "bpmn20.xml", bpmnModelForData).deploy();
        modelForData.setDeploymentId(deploy.getId());
        // 2.4 保存模型
        repositoryService.saveModel(modelForData);
    }

    @Test
    public void bypassUnionCode() throws Exception{
        // 创建流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        // 获取仓库服务
        RepositoryService repositoryService = processEngine.getRepositoryService();

        // 部署流程定义文件
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("activiti/unionCode.bpmn20.xml")
                .deploy();
    }
}
