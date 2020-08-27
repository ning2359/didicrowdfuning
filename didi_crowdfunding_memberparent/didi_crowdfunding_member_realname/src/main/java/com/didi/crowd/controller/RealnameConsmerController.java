package com.didi.crowd.controller;
import com.didi.crowd.CrowdConstant;
import com.didi.crowd.CrowdUtil;
import com.didi.crowd.ResultEntity;
import com.didi.crowd.api.MySqlRemoteService;
import com.didi.crowd.api.RedisRemoteService;
import com.didi.crowd.config.OSSProperties;
import com.didi.crowd.entrty.PO.TCert;
import com.didi.crowd.entrty.VO.LoginMemberVO;
import com.didi.crowd.entrty.VO.RealNameVO;
import com.didi.realname.config.PassListener;
import com.didi.realname.config.RefuseListener;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Controller
public class RealnameConsmerController {
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private OSSProperties properties;
    @Autowired
    private  JavaMailSenderImpl mailSender;
    @Autowired
    private MySqlRemoteService mySqlRemoteService;
    @Autowired
    private RedisRemoteService redisRemoteService;

    private Logger logger = LoggerFactory.getLogger(RealnameConsmerController.class);
    @RequestMapping("do/apply/page")
    public String doApplyPage(@RequestParam("realnameId") Integer accttype,
                              HttpSession session){
        RealNameVO realNameVO = new RealNameVO();
        realNameVO.setAccttype(accttype);
        session.setAttribute(CrowdConstant.ATTR_NAME_REALNAME,realNameVO);
    logger.info("realnameId"+accttype);
        return "realname_info";
    }
    @RequestMapping("member/info")
    public String memberInfo(RealNameVO realNameVO,
                             HttpSession session,
                             Map<String,Object>map){

        LoginMemberVO loginMemberVO = (LoginMemberVO) session.getAttribute(CrowdConstant.MEMBER);
        logger.info("loginMemberVO"+loginMemberVO.toString());
        Integer memberVOId = loginMemberVO.getId();
        realNameVO.setMemberId(memberVOId);

        RealNameVO realName = (RealNameVO) session.getAttribute(CrowdConstant.ATTR_NAME_REALNAME);
        Integer accttype = realName.getAccttype();
        realNameVO.setAccttype(accttype);
        logger.info("realNameVO身份证 电话号"+realNameVO.toString());
        session.setAttribute(CrowdConstant.ATTR_NAME_REALNAME,realName);
        logger.info("accttype"+accttype);
        ResultEntity<List<TCert>> certs =mySqlRemoteService.queryCertsByAccttype(accttype);
        String result = certs.getResult();
        if (ResultEntity.FAILED.equals(result)){
            map.put(CrowdConstant.ATTR_NAME_MESSAGE,"系统查询错误");
            return "system_error";
        }
        List<TCert> data = certs.getData();
        if (!(data!=null&&data.size()>0)){
            map.put(CrowdConstant.ATTR_NAME_MESSAGE,"集合为空");
            return "system_error";
        }
        map.put("certs",data);
        logger.info("data"+data.toString());
        session.setAttribute(CrowdConstant.ATTR_NAME_REALNAME,realNameVO);
        return "realname_qua_pic";
    }
    @RequestMapping("save/qualification/pic")
    public String saveQualificationPic(
            List<MultipartFile> qualificationPictureList,
            @RequestParam("certIds") List<Integer> certIds,
            HttpSession session,
            Map<String,Object> map
        ) throws IOException {
        logger.info("certIds"+certIds.toString());
        RealNameVO realName = (RealNameVO) session.getAttribute(CrowdConstant.ATTR_NAME_REALNAME);
        if (realName==null){
            map.put(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_TEMPLE_PROJECT_MISSING);
            return "system_error";
        }
        Integer memberId = realName.getMemberId();
        logger.info("memberId"+memberId);
        if (qualificationPictureList.size()!=certIds.size()){
            map.put(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_REALNAME_PIC_CERT_NOT);
            return "realname_qua_pic";
        }
        logger.info("realName"+realName.toString());
        int i = 0;
        for (MultipartFile qualificationPicture :qualificationPictureList) {
            if (qualificationPicture.isEmpty()){
                map.put(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_REALNAME_PIC_EMPTY);
                return "system_error";
            }
            ResultEntity<String> uploadQualificationPictureList = CrowdUtil.uploadFileToOss(
                    properties.getEndPoint(),
                    properties.getAccessKeyId(),
                    properties.getAccessKeySecret(),
                    qualificationPicture.getInputStream(),
                    properties.getBucketName(),
                    properties.getBucketDomain(),
                    qualificationPicture.getOriginalFilename()
            );
            String detailResult = uploadQualificationPictureList.getResult();
            if (ResultEntity.FAILED.equals(detailResult)){
                map.put(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_PIC_UPLOAD_FAILED);
                return "system_error";
            }
            String detailData = uploadQualificationPictureList.getData();
            logger.info("detailData"+detailData);
            logger.info("certIds"+certIds.get(i));
            ResultEntity<String> resultEntity= mySqlRemoteService.saveQualificationCert(memberId,certIds.get(i),detailData);
            String saveResult = resultEntity.getResult();
            if (ResultEntity.FAILED.equals(saveResult)){
                map.put(CrowdConstant.ATTR_NAME_MESSAGE,resultEntity.getMessage());
                return "realname_qua_pic";
            }
            i++;
        }
        session.setAttribute(CrowdConstant.ATTR_NAME_REALNAME,realName);
        return "realname_email";
    }
    @ResponseBody
    @RequestMapping("/send/autucode")
    public ResultEntity<String> sendAutucode(
            @RequestParam(value = "email",required = false)String email,
            HttpSession session,
            Map<String,Object> map){
        RealNameVO realName = (RealNameVO) session.getAttribute(CrowdConstant.ATTR_NAME_REALNAME);
            if (realName==null){
               return ResultEntity.failed(CrowdConstant.MESSAGE_TEMPLE_PROJECT_MISSING);
         }
            if (email==null){
                email= realName.getEmail();
            }
             realName.setEmail(email);
            logger.info("email"+email);
             // 4 位验证码
            StringBuilder authcode = new StringBuilder();
            for ( int i = 0; i < 4; i++ ) {
                authcode.append(new Random().nextInt(10));
            }
            String key = CrowdConstant.REDIS_CODE_PREFIX+email;
            logger.info("验证码为"+authcode);
            ResultEntity<String> saveCodeResultEntity = redisRemoteService.setRedisKeyValueRemoteWithTimeout(key, String.valueOf(authcode), 15, TimeUnit.MINUTES);
        String result = saveCodeResultEntity.getResult();
        if (ResultEntity.FAILED.equals(result)){
            return ResultEntity.failed(CrowdConstant.MESSAGE_CODE_SEND_FILED);
        }
            String code = authcode.toString();
        try {
            String postCount = "admin@didi.com";
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setFrom(postCount);
            mimeMessageHelper.setSubject("实名专用【didi众筹科技】");//邮件主题
            StringBuilder sb = new StringBuilder();
            sb.append("<html><head></head>");
            sb.append("<body><h1>实名认证专用</h1><p>【didi众筹科技】您的验证码是：")
                    .append(code)
                    .append("，10分钟内有效，请勿将验证码透露给他人，谨防诈骗。</p></body>");
            sb.append("</html>");
            // 启用html
            mimeMessageHelper.setText(sb.toString(), true);
            // 发送邮件
            mailSender.send(mimeMessage);
            logger.info("邮件已经从"+postCount+"->发送到账户"+email);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
        // 查询实名认证的审批流程定义
            session.setAttribute(CrowdConstant.ATTR_NAME_REALNAME,realName);
        return ResultEntity.successWithoutData();
    }

    @RequestMapping("/to/comfirm/code/page")
    public String toComfirmCodePage(){
        logger.info("开始返回");
        return "realname_confirm_code";
    }

    @RequestMapping("confirm/code")
    public String confirmCode(
            @RequestParam("code") String code,
            HttpSession session,
            Map<String,Object> map
        ){
        RealNameVO realName = (RealNameVO) session.getAttribute(CrowdConstant.ATTR_NAME_REALNAME);
        if (realName==null){
            map.put(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_TEMPLE_PROJECT_MISSING);
            return "system_error";
        }
        String email = realName.getEmail();
        logger.info("email"+email);
        String key = CrowdConstant.REDIS_CODE_PREFIX+email;
        ResultEntity<String> redisStringValueByKeyRemote = redisRemoteService.getRedisStringValueByKeyRemote(key);
        String result = redisStringValueByKeyRemote.getResult();
        if (ResultEntity.FAILED.equals(result)){
            map.put(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_CODE_NOT_EMAIL_EXISTS);
            return "realname_confirm_code";
        }
        String data = redisStringValueByKeyRemote.getData();
        if (data==null){
            map.put(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_CODE_NOT_EMAIL_EXISTS);
            return "realname_confirm_code";
        }
        if (!(data.equals(code))){
            map.put(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_CODE_NOT_EMAIL_EXISTS);
            return "realname_confirm_code";
        }
        // 启动审批流程
        Map<String, Object> varMap = new HashMap<String, Object>();
        varMap.put("passListener", new PassListener());
        varMap.put("refuseListener", new RefuseListener());

        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
        ProcessDefinition processDefinition = query.processDefinitionKey("realname")
                .latestVersion()
                .singleResult();
        logger.info("ProcessDefinition的Id"+processDefinition.getId());

        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(), varMap);
        logger.info("流程实例Id"+processInstance.getId());

        ResultEntity<String> removeRedisKeyRemote = redisRemoteService.removeRedisKeyRemote(key);
        logger.info(removeRedisKeyRemote.getResult()+"  "+redisStringValueByKeyRemote.getMessage());

        realName.setProcdefId(processInstance.getId());
        session.setAttribute(CrowdConstant.ATTR_NAME_REALNAME,realName);
        LoginMemberVO loginMemberVO = (LoginMemberVO) session.getAttribute(CrowdConstant.MEMBER);
        loginMemberVO.setAuthstatus(1);
        session.setAttribute(CrowdConstant.MEMBER,loginMemberVO);
        //开始保存数据
        ResultEntity<String> resultEntity= mySqlRemoteService.saveRealnameRemote(realName);
        String saveResult = resultEntity.getResult();
        if (ResultEntity.FAILED.equals(saveResult)){
            map.put(CrowdConstant.ATTR_NAME_MESSAGE,resultEntity.getMessage());
            return "realname_confirm";
        }
        return "redirect:http://localhost:80/auth/do/member/center";
    }
}
