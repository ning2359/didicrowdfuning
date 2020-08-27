package com.didi.realname.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.didi.crowd.ResultEntity;
import com.didi.crowd.entrty.PO.TMember;
import com.didi.entrty.utils.Audit;
import com.didi.entrty.utils.CertImg;
import com.didi.entrty.utils.Ticket;
import com.didi.realname.config.PassListener;
import com.didi.realname.config.RefuseListener;
import com.didi.realname.mapper.RealnameMapper;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class RealnameController {
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private RealnameMapper realnameMapper;

    private Logger logger = LoggerFactory.getLogger(RealnameController.class);
    @ResponseBody
    @RequestMapping("/realname/show/list.json")
    public ResultEntity<Page<Map<String,Object>>> realnameShowList(
            @RequestParam(value = "current" ,defaultValue = "1") Integer current,
            @RequestParam(value = "pageSize" ,defaultValue = "5") Integer pageSize,
            @RequestParam(value = "keyWord",defaultValue = "")String keyWord,
            HttpSession session
    ){
        try {
        TaskService taskService = processEngine.getTaskService();
        TaskQuery query = taskService.createTaskQuery();
        List<Task> tasks = query.processDefinitionKey("realname")
                .taskCandidateGroup("realnameGroup")
              .listPage((current-1)*pageSize,pageSize);
        List<Map<String, Object>> taskMapList = new ArrayList<Map<String, Object>>();//避免JSON数据转换出错
        for ( Task task : tasks ) {
            Map<String, Object> taskMap = new HashMap<String, Object>();
            taskMap.put("id", task.getId());
            taskMap.put("name", task.getName());
            logger.info("task.getId()"+task.getId());
            logger.info("任务名称"+task.getName());
            //通过任务表的流程定义id查询流程定义
            RepositoryService repositoryService = processEngine.getRepositoryService();
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                    .processDefinitionId(task.getProcessDefinitionId())
                    .singleResult();
            logger.info("查询流程定义"+processDefinition.getId());
            taskMap.put("proInstId",task.getProcessInstanceId());
            taskMap.put("procDefName", processDefinition.getName());
            taskMap.put("procDefVersion", processDefinition.getVersion());
            // 通过流程查找流程审批单，再查询会员信息
            Ticket ticket= realnameMapper.queryTicketByPiid(task.getProcessInstanceId());
            logger.info("ticket"+ticket.toString());
            Integer memberId = ticket.getMemberId();
            TMember member = realnameMapper.queryMemberByMemberId(memberId);
            logger.info("member"+member.toString());
            taskMap.put("memberName",member.getUserName());
            taskMap.put("memberid", member.getId());
            taskMapList.add(taskMap);
        }
        // 获取数据的总条数
        int count = (int)query.count(); //同一个query 对象,查询条件是一样的
        Page<Map<String, Object>> page = new Page<Map<String, Object>>(current,pageSize);
        page.setRecords(taskMapList);
        page.setTotal(count );
        page.setCurrent(current);
        page.setSize(pageSize);
        logger.info("page"+page.toString());
        return ResultEntity.successWithData(page);
        }catch (Exception e){
            e.printStackTrace();
            return ResultEntity.failed("查询失败"+e.getMessage());
        }
    }
    @ResponseBody
    @RequestMapping("/realname/get/all/info.json")
    public ResultEntity<Audit> realnameGetallinfo(
            @RequestParam("memberId") Integer memberId
    ){
        try {
            logger.info("memberId"+memberId);
            TMember member = realnameMapper.queryMemberByMemberId(memberId);
            logger.info("member"+member.toString());
            List<CertImg> certImgs = realnameMapper.queryCertImgsByMemberid(memberId);
            Audit audit = new Audit();
            BeanUtils.copyProperties(member,audit);
            audit.setCertImgList(certImgs);
            logger.info("audit"+audit.toString());
            logger.info("certImgs"+certImgs.toString());
            return ResultEntity.successWithData(audit);
        }catch (Exception e){
            e.printStackTrace();
            return ResultEntity.failed("查询错误"+e.getMessage());
        }
    }
    @ResponseBody
    @RequestMapping("/realname/cert/pass.json")
    public ResultEntity<String> realnameCertPass(
            @RequestParam("taskId") String taskId,
            @RequestParam("memberId") Integer memberId,
//            @RequestParam("prodef") String prodef,
            HttpSession session
    ){
        try {
            logger.info("taskId"+taskId);
            logger.info("memberId"+memberId);
//            logger.info("prodef"+prodef);
            SecurityContext context_session = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
            Authentication authentication = context_session.getAuthentication();
            //获得User对象
            User user = (User) authentication.getPrincipal(); //获得用户名
            String username = user.getUsername();
            logger.info("username"+username);
            TaskService taskService = processEngine.getTaskService();
            taskService.claim(taskId,username);
//            taskService.setVariable(taskId,"passListener",new PassListener());
            taskService.setVariable(taskId, "flag", true);
            taskService.setVariable(taskId, "memberId", memberId);
            // 传递参数，让流程继续执行
            taskService.complete(taskId);
            return ResultEntity.successWithoutData();
        } catch ( Exception e ) {
            e.printStackTrace();
            return ResultEntity.failed("处理失败"+e.getMessage());
        }
    }
    @ResponseBody
    @RequestMapping("/realname/cert/refuse.json")
    public ResultEntity<String> realnameCertRefuse(
            @RequestParam("taskId") String taskId,
            @RequestParam("memberId") Integer memberId,
            HttpSession session
    ){
        try {
            logger.info("taskId"+taskId);
            logger.info("memberId"+memberId);
//            logger.info("prodef"+prodef);
            SecurityContext context_session = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
            Authentication authentication = context_session.getAuthentication();
            //获得User对象
            User user = (User) authentication.getPrincipal(); //获得用户名
            String username = user.getUsername();
            logger.info("username"+username);
            TaskService taskService = processEngine.getTaskService();
            taskService.claim(taskId,username);
            taskService.setVariable(taskId, "flag", false);
            taskService.setVariable(taskId, "memberId", memberId);
//            taskService.setVariable(taskId,"refuseListener",new RefuseListener());
            // 传递参数，让流程继续执行
            taskService.complete(taskId);
//            HistoryService historyService = processEngine.getHistoryService();
//            HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery();
//            HistoricProcessInstance historicProcessInstance = historicProcessInstanceQuery.processInstanceId().finished().singleResult();
//            if (historicProcessInstance!=null){
//                logger.info("流程处理完成");
//            }
            return ResultEntity.successWithoutData();
        } catch ( Exception e ) {
            e.printStackTrace();
            return ResultEntity.failed("处理失败"+e.getMessage());
        }
    }
}
