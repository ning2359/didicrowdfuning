package com.didi.admin.controller;
import com.didi.crowd.CrowdUtil;
import com.didi.crowd.ResultEntity;
import com.didi.entrty.utils.ParamData;
import com.didi.entrty.utils.Student;
import com.didi.entrty.admin.TAdmin;
import com.didi.admin.service.impl.TAdminServiceImpl;
import com.didi.realname.config.PassListener;
import com.didi.realname.config.RefuseListener;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TAdminController {
    @Autowired
    private ProcessEngine processEngine;
    private Logger logger = LoggerFactory.getLogger(TAdminController.class);
    @Autowired
    private TAdminServiceImpl adminService;

    @RequestMapping("/test/activiti.html")
    public String  testActiviti(Map<String,Object> map) {
        // 启动审批流程
        Map<String, Object> varMap = new HashMap();
        varMap.put("passListener", new PassListener());
        varMap.put("refuseListener", new RefuseListener());

        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
        ProcessDefinition processDefinition = query.processDefinitionKey("realname")
                .latestVersion()
                .singleResult();

        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(), varMap);
        logger.info("流程实例Id" + processInstance.getId());
        TaskService taskService = processEngine.getTaskService();
        TaskQuery taskQuery = taskService.createTaskQuery();
        List<Task> realnameGroup = taskQuery.taskCandidateGroup("realnameGroup").list();
        for (Task task : realnameGroup) {
            logger.info("任务Id" + task.getId());
            logger.info("任务Name" + task.getName());
            taskService.claim(task.getId(), "aa");
            taskService.setVariable(task.getId(), "flag", true);
            taskService.setVariable(task.getId(), "memberId", 4);
            taskService.complete(task.getId());
            return "result";
        }
        return "result";
    }
    @RequestMapping("/querry.html")
    public String querrtAll(Map<String,Object> map){
        List<TAdmin> tAdmins = adminService.querryAdmin();
        map.put("list",tAdmins);
//        System.out.println(1/0);
        return "result";
    }
    @ResponseBody
    @RequestMapping("/textjson1.html")
    public String  textjson1(@RequestParam("array[]") List<Integer> list, HttpServletRequest request){
        logger.info("list"+list);
        boolean judgeRequestType = CrowdUtil.judgeRequestType(request);
        logger.info(String.valueOf(judgeRequestType));
        return "success";
    }
   @ResponseBody
    @RequestMapping("/textjson2.html")
    public String textjson2( ParamData paramData){
        for (Integer aa:paramData.getList()){
            logger.info("number"+aa);
        }
        return "success";
    }
    @ResponseBody
    @RequestMapping(value = "/textjson3.html")
    public String testReceiveArrayThree(@RequestBody List<Integer> list) {
        for (Integer number : list) {
            logger.info("number"+number);
        }
        return "success";
    }
    @ResponseBody
    @RequestMapping("testComplex.json")
    public ResultEntity<Student> testComplex(@RequestBody Student student){
        logger.warn(String.valueOf(student));
        ResultEntity<Student> resultEntity = ResultEntity.successWithData(student);
        return resultEntity;
    }

}

