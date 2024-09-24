package com.mechoy.flowable.controller;

import com.mechoy.flowable.vo.Result;
import com.mechoy.flowable.vo.TaskVO;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.*;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/leave")
@Slf4j
public class LeaveController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private ApplicationContext applicationContext;


    @GetMapping("/deploy/{file}")
    public Result deploy(@PathVariable("file") String file) {
        // 获取默认的流程引擎
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        // 获取 RepositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();

        // 部署流程定义
        repositoryService.createDeployment()
                .addClasspathResource(file + ".bpmn20.xml")
                .deploy();

        return new Result(true, "流程部署成功");
    }

    @RequestMapping("/getBeans")
    @ResponseBody
    public void getAllBeans() {
        Map<String, Object> beansOfType = applicationContext.getBeansOfType(Object.class);
        for (Map.Entry<String, Object> entry : beansOfType.entrySet()) {
            String beanName = entry.getKey();
            Object beanInstance = entry.getValue();
            Class<?> beanType = beanInstance.getClass();
            System.out.println(beanType.getName());
        }
    }

    @GetMapping("/startProcess/{file}/{key}")
    public Result startProcess(@PathVariable("file") String file, @PathVariable("key") String key) {
        // 1. 创建流程引擎配置
        ProcessEngineConfiguration cfg = ProcessEngineConfiguration
                .createStandaloneInMemProcessEngineConfiguration();

        // 2. 获取流程引擎实例
        ProcessEngine processEngine = cfg.buildProcessEngine();

        // 3. 获取 RepositoryService 实例
        RepositoryService repositoryService = processEngine.getRepositoryService();

        // 4. 部署 BPMN 流程定义
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource(file + ".bpmn20.xml") // 请替换为你的 BPMN 文件路径
                .name("Test Process Deployment")
                .deploy();

        System.out.println("流程定义部署成功，部署ID：" + deployment.getId());

        // 5. 获取 RuntimeService 实例
        RuntimeService runtimeService = processEngine.getRuntimeService();

        // 6. 创建流程变量
        Map<String, Object> variables = new HashMap<>();
        // 7. 启动流程实例，根据流程定义的 key
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key, variables);

        // 8. 输出流程实例ID，确认启动成功
        return new Result(true, "流程部署成功", processInstance.getId());
    }

    @GetMapping("teacherList")
    public Result teacherList() {
        //此处.taskCandidateGroup("a")的值“a”即是画流程图时辅导员审批节点"分配用户-候选组"中填写的值
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("a").list();
        List<TaskVO> taskVOList = tasks.stream().map(task -> {
            Map<String, Object> variables = taskService.getVariables(task.getId());
            return new TaskVO(task.getId(), variables.get("day").toString(), variables.get("studentName").toString());
        }).collect(Collectors.toList());
        log.info("任务列表：" + tasks);
        if (tasks == null || tasks.size() == 0) {
            return new Result(false, "没有任务");
        }
        return new Result(true, "获取成功", taskVOList);
    }

    /**
     * 辅导员批准
     *
     * @param taskId 任务ID，非流程id
     */
    @GetMapping("teacherApply/{taskId}")
    public Result teacherApply(@PathVariable("taskId") String taskId) {
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("a").list();
        Task task = taskService.createTaskQuery().taskCandidateGroup("a").taskId(taskId).singleResult();
        if (task == null) {
            return new Result(false, "没有任务");
        }
        //通过审核
        HashMap<String, Object> map = new HashMap<>();
        map.put("outcome", "通过");
//        for (Task task : tasks) {
//            taskService.complete(task.getId(), map);
//        }
        taskService.complete(task.getId(), map);
        return new Result(true, "审批成功");
    }

    /**
     * 辅导员拒绝
     *
     * @param taskId 任务ID，非流程id
     */
    @GetMapping("teacherReject/{taskId}")
    public Result teacherReject(@PathVariable("taskId") String taskId) {
        Task task1 = taskService.createTaskQuery().taskCandidateGroup("a").taskId(taskId).singleResult();
        //Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task1 == null) {
            return new Result(false, "没有任务");
        }
        //通过审核
        HashMap<String, Object> map = new HashMap<>();
        map.put("outcome", "驳回");
        taskService.complete(task1.getId(), map);
        return new Result(true, "审批失败");
    }

    /**
     * 院长获取审批管理列表
     */
    @GetMapping("deanList")
    public Result deanList() {
        //此处.taskCandidateGroup("b")的值“b”即是画流程图时辅导员审批节点"分配用户-候选组"中填写的值
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("b").list();
        List<TaskVO> taskVOList = tasks.stream().map(task -> {
            Map<String, Object> variables = taskService.getVariables(task.getId());
            return new TaskVO(task.getId(), variables.get("day").toString(), variables.get("studentName").toString());
        }).collect(Collectors.toList());
        if (tasks == null || tasks.size() == 0) {
            return new Result(false, "没有任务");
        }
        return new Result(true, "获取成功", taskVOList);
    }

    /**
     * 院长批准
     *
     * @param taskId 任务ID，非流程id
     * @return
     */
    @GetMapping("deanApply/{taskId}")
    public Result apply(@PathVariable("taskId") String taskId) {
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("b").list();
        //Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (tasks == null) {
            return new Result(false, "没有任务");
        }
        //通过审核
        HashMap<String, Object> map = new HashMap<>();
        map.put("outcome", "通过");
        for (Task task : tasks) {
            taskService.complete(task.getId(), map);
        }
        return new Result(true, "审批成功");
    }

    /**
     * 院长拒绝
     *
     * @param taskId 任务ID，非流程id
     * @return
     */
    @GetMapping("deanReject/{taskId}")
    public Result deanReject(@PathVariable("taskId") String taskId) {
//        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("b").list();
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            return new Result(false, "没有任务");
        }
        //通过审核
        HashMap<String, Object> map = new HashMap<>();
        map.put("outcome", "驳回");
//        for (Task task : tasks) {
//            taskService.complete(task.getId(), map);
//        }
        taskService.complete(task.getId(), map);
        return new Result(true, "审批成功");
    }

    /**
     * 再次申请
     *
     * @param piId 流程id
     * @param day
     * @return
     */
    @GetMapping("subAgain/{piId}/{day}")
    public Result subAgain(@PathVariable("piId") String piId, @PathVariable("day") Integer day) {
        Task task = taskService.createTaskQuery().processInstanceId(piId).singleResult();
        if (Objects.isNull(task)) {
            return new Result(false, "没有任务");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("day", day);
        taskService.complete(task.getId(), map);
        return new Result(true, "申请成功");
    }

}
