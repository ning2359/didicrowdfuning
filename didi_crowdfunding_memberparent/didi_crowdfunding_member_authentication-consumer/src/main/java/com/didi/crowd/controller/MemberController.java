package com.didi.crowd.controller;

import com.didi.crowd.CrowdConstant;
import com.didi.crowd.CrowdUtil;
import com.didi.crowd.ResultEntity;
import com.didi.crowd.api.MySqlRemoteService;
import com.didi.crowd.api.RedisRemoteService;
import com.didi.crowd.config.ShortMessageProperties;
import com.didi.crowd.entrty.PO.TMember;
import com.didi.crowd.entrty.VO.LoginMemberVO;
import com.didi.crowd.entrty.VO.RegisterMemberVO;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Controller
public class MemberController {
    @Autowired
    private ShortMessageProperties properties;

    @Autowired
    private RedisRemoteService redisRemoteService;

    @Autowired
    private MySqlRemoteService mySqlRemoteService;

    private Logger logger = LoggerFactory.getLogger(MemberController.class);

    @RequestMapping("/auth/member/logout")
    public String authMembeLogout(HttpSession session){
        session.invalidate();
        return "redirect:http://localhost:80/";
    }

    @RequestMapping("/auth/member/do/login")
    public String authMemberDoLogin(@RequestParam("loginAcct") String loginAcct,
                                    @RequestParam("userPasswd") String userPasswd,
                                    Map<String,String> map,
                                    HttpSession session){
        ResultEntity<TMember> loginacctRemote = mySqlRemoteService.getMemberByLoginacctRemote(loginAcct);
        String result = loginacctRemote.getResult();
        if (ResultEntity.FAILED.equals(result)){
            map.put(CrowdConstant.ATTR_NAME_MESSAGE,loginacctRemote.getMessage());
            return "member_log";
        }
        TMember member = loginacctRemote.getData();
        if (member==null){
            map.put(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_LOGIN_FAILED);
            return "member_log";
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String dataUserPasswd = member.getUserPasswd();
        boolean matches = encoder.matches(userPasswd,dataUserPasswd);
        if (!(matches)){
            map.put(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_LOGIN_PWD);
            return "member_log";
        }
        LoginMemberVO loginMemberVO = new LoginMemberVO();
        BeanUtils.copyProperties(member,loginMemberVO);
        member = null;
        session.setAttribute(CrowdConstant.MEMBER,loginMemberVO);
        logger.info(String.valueOf(loginMemberVO));
        return "redirect:http://localhost:80/auth/do/member/center";
    }

    @RequestMapping("/auth/do/member/register")
    public String authDoMemberRegister(RegisterMemberVO memberVO ,Map<String,String> map){
        String phoneNum = memberVO.getPhoneNum();
        String key = CrowdConstant.REDIS_CODE_PREFIX+phoneNum;
        ResultEntity<String> redisStringValueByKeyRemote = redisRemoteService.getRedisStringValueByKeyRemote(key);
        String result = redisStringValueByKeyRemote.getResult();
        if (ResultEntity.FAILED.equals(result)){
            map.put(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_CODE_NOT_EXISTS);
            return "system_error";
        }
        String data = redisStringValueByKeyRemote.getData();
        if (data==null){
            map.put(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_CODE_NOT_EXISTS);
            return "system_error";
        }
        String code = memberVO.getCode();
        if (!(Objects.equals(data,code))){
            map.put(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_STRING_INVALIDATE);
            return "system_error";
        }
        ResultEntity<String> removeRedisKeyRemote = redisRemoteService.removeRedisKeyRemote(key);
        logger.info(removeRedisKeyRemote.getResult()+"  "+redisStringValueByKeyRemote.getMessage());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String formUserPasswd = memberVO.getUserPasswd();
        String encodeUserPasswd = encoder.encode(formUserPasswd);
        memberVO.setUserPasswd(encodeUserPasswd);
        TMember memberPO = new TMember();
        BeanUtils.copyProperties(memberVO,memberPO);
        memberPO.setAuthstatus(0);

        ResultEntity<String> stringResultEntity = mySqlRemoteService.authPOMemberRegister(memberPO);
        if (ResultEntity.FAILED.equals(stringResultEntity.getResult())){
            map.put(CrowdConstant.ATTR_NAME_MESSAGE,stringResultEntity.getMessage());
            return "system_error";
        }
        return "redirect:http://localhost:80/auth/member/to/login/page";
    }
    @ResponseBody
    @RequestMapping("/auth/member/send/short/message.json")
    public ResultEntity<String> authMemberSendShortMessage(@RequestParam("phoneNum") String phoneNum){
        logger.info(String.valueOf(properties));
        logger.info("phoneNum"+phoneNum);
        if (!(phoneNum != null &&  phoneNum.length()==11)){
            logger.warn("dsadsad");
            return ResultEntity.failed("手机号输入不正确");
        }
        ResultEntity<String> result = CrowdUtil.sendCodeByShortMessage(
                properties.getHost(),
                properties.getPath(),
                properties.getMethod(),
                phoneNum,
                properties.getAppCode(),
                properties.getSign(),
                properties.getSkin()
        );
        logger.info("result"+result.toString());
        if (ResultEntity.SUCCESS.equals(result.getResult())){
            String code = result.getData();
            String key = CrowdConstant.REDIS_CODE_PREFIX+phoneNum;
            ResultEntity<String> saveCodeResultEntity = redisRemoteService.setRedisKeyValueRemoteWithTimeout(key, code, 15, TimeUnit.MINUTES);
            if (ResultEntity.SUCCESS.equals(saveCodeResultEntity.getResult())){
                return ResultEntity.successWithoutData();
            }else {
                return saveCodeResultEntity;
            }
        }else {
            return result;
        }
    }

}
