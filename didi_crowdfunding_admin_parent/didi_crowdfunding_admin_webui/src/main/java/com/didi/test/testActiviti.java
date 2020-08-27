package com.didi.test;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.didi.crowd.entrty.PO.TMember;
import com.didi.entrty.utils.Ticket;

import com.didi.realname.mapper.RealnameMapper;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:admin/applicationContext.xml","classpath:admin/applicationContext-*.xml"})
public class testActiviti {
    @Autowired
    private RealnameMapper realnameMapper;
    @Autowired
    private ProcessEngine processEngine;


    private Logger logger = LoggerFactory.getLogger(testActiviti.class);


    @Test
    public void createDeploy() {
        logger.info(String.valueOf(processEngine));
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("diagram/realname.bpmn")
                .addClasspathResource("diagram/realname.png")
                .deploy();
        logger.info("流程部署id:" + deploy.getId());
        logger.info("流程部署名称:" + deploy.getName());
    }

    @Test
    public void createQuery() {
        // 启动审批流程
        Map<String, Object> varMap = new HashMap();
//        varMap.put("passListener", new PassListener());
//        varMap.put("refuseListener", new RefuseListener());

        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
        ProcessDefinition processDefinition = query.processDefinitionKey("realname")
                .latestVersion()
                .singleResult();

        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(), varMap);
        logger.info("流程实例Id"+processInstance.getId());
        TaskService taskService = processEngine.getTaskService();
        TaskQuery taskQuery = taskService.createTaskQuery();
        List<Task> realnameGroup = taskQuery.taskCandidateGroup("realnameGroup").list();
        for(Task task:realnameGroup){
            logger.info("任务Id"+task.getId());
            logger.info("任务Name"+task.getName());
            taskService.claim(task.getId(),"aa");
            taskService.setVariable(task.getId(),"flag",true);
            taskService.setVariable(task.getId(),"memberId",4);
            taskService.complete(task.getId());
        }
    }

    @Test
    public void aa() {
        // 查询任务数据
        TaskService taskService = processEngine.getTaskService();
        TaskQuery query = taskService.createTaskQuery();
        List<Task> tasks = query.processDefinitionKey("realname")
                .taskCandidateGroup("realnameGroup")
                .list();
        List<Map<String, Object>> taskMapList = new ArrayList<Map<String, Object>>();//避免JSON数据转换出错
        for (Task task : tasks) {
            Map<String, Object> taskMap = new HashMap<String, Object>();
            taskMap.put("id", task.getId());
            taskMap.put("name", task.getName());
            logger.info("task.getId()" + task.getId());
            logger.info("任务名称" + task.getName());
            //通过任务表的流程定义id查询流程定义
            RepositoryService repositoryService = processEngine.getRepositoryService();
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                    .processDefinitionId(task.getProcessDefinitionId())
                    .singleResult();
            logger.info("查询流程定义" + processDefinition.getId());
            taskMap.put("procDefName", processDefinition.getName());
            taskMap.put("procDefVersion", processDefinition.getVersion());
            // 通过流程查找流程审批单，再查询会员信息
            Ticket ticket = realnameMapper.queryTicketByPiid(task.getProcessInstanceId());
            logger.info("ticket" + ticket.toString());
            Integer memberId = ticket.getMemberId();
            TMember member = realnameMapper.queryMemberByMemberId(memberId);
            logger.info("member" + member.toString());
            taskMap.put("memberName", member.getUserName());
            taskMap.put("memberid", member.getId());
            taskMapList.add(taskMap);
        }
        // 获取数据的总条数
        int count = (int) query.count(); //同一个query 对象,查询条件是一样的
        Page<Map<String, Object>> page = new Page<Map<String, Object>>(1, 5);
        page.setRecords(taskMapList);
        page.setTotal(count);
        page.setCurrent(1);
        page.setSize(5);
        logger.info("page" + page.toString());
    }


    @Test
    public void testTaskCollection() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("listenEvents")
                .latestVersion()
                .singleResult();
        logger.info(processDefinition.toString());

//        Map<String, Object> varMap = new HashMap();
//        varMap.put("yesListener", new PassListener());
//        varMap.put("noListener", new RefuseListener());
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processDefinition.getId()).singleResult();
        TaskService taskService = processEngine.getTaskService();
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
        HistoryService historyService = processEngine.getHistoryService();
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(processInstance.getId())
                .finished()
                .singleResult();
        logger.info(historicProcessInstance!=null? "流程完成":"流程未完成");
    }
    public void testDeploymentQuery(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
        List<ProcessDefinition> list = query.list();
        for (ProcessDefinition processDefinition : list) {
            System.out.println(processDefinition.getName());
            System.out.println(processDefinition.getKey());
            System.out.println(processDefinition.getVersion());
            System.out.println(processDefinition.getId());
            System.out.println("-----------");
        }
    }
}
