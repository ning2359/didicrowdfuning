package com.didi.crowd.test;

import com.didi.realname.config.PassListener;
import com.didi.realname.config.RefuseListener;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivitiTest {
    private Logger logger = LoggerFactory.getLogger(ActivitiTest.class);
    @Autowired
    private DataSource dataSource;
    @Autowired
    private ProcessEngine processEngine;
    @Test
    public void testSql() throws SQLException {

        logger.info(dataSource.toString());
        logger.info(String.valueOf(dataSource.getConnection()));
//        logger.info(processEngine.toString());
    }
    @Test
    public void testActiviti(){
        logger.info(processEngine.toString());
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionKey("sendEmail")
                .latestVersion()
                .singleResult();
        logger.info(processDefinition.getId());
    }
    @Test
    public void testProcess() throws SQLException {
        Map<String, Object> varMap = new HashMap<String, Object>();
        varMap.put("passListener", new PassListener());
        varMap.put("refuseListener", new RefuseListener());
        // 查询实名认证的审批流程定义
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
        ProcessDefinition processDefinition = query.processDefinitionKey("realname")
                .latestVersion()
                .singleResult();
        logger.info("ProcessDefinition的Id"+processDefinition.getId());
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(), varMap);
        logger.info("流程实例Id"+processInstance.getId());
    }
    @Test
    public void createDeploy() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("templates/realname.bpmn")
                .addClasspathResource("templates/realname.png")
                .deploy();
        logger.info("流程部署id:" + deploy.getId());
        logger.info("流程部署名称:" + deploy.getName());
    }
    @Test
    public void testProcessInstance(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //查询流程定义
        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionKey("relname")
                .latestVersion()
                .singleResult();
        logger.info("processDefinition="+processDefinition.toString());
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());
        System.out.println(processInstance);
    }
    @Test
    public void task(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        // 让申请流程继续执行
        ProcessDefinition pd = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionKey("relname")
                .latestVersion()
                .singleResult();
        TaskService taskService = processEngine.getTaskService();
        TaskQuery query = taskService.createTaskQuery();
        List<Task> tasks = query.processDefinitionKey("relname")
                .taskCandidateGroup("realnameGroup")
               .list();
        Map<String, Object> taskMap = new HashMap<String, Object>();
        taskMap.put("passListener", new PassListener());
        taskMap.put("refuseListener", new RefuseListener());
        taskMap.put("flag",true);
        taskService = processEngine.getTaskService();
        TaskQuery taskQuery = taskService.createTaskQuery();
        List<Task> taskList = taskQuery.taskAssignee("zhangsan").list();
        long zhangsan = taskQuery.taskAssignee("zhangsan").count();
        logger.info(String.valueOf(zhangsan));
        if (taskList!=null&&taskList.size()>0){
            for (Task task : taskList) {
                taskService.setVariable(task.getId(), "flag", false);
                taskService.complete(task.getId());
            }
        }


    }
}
