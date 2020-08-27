package com.didi.crowd.controller;

import com.didi.crowd.CrowdConstant;
import com.didi.crowd.ResultEntity;
import com.didi.crowd.entrty.PO.TMember;
import com.didi.crowd.service.impl.TMemberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author jobob
 * @since 2020-06-29
 */
@RestController
public class TMemberController {
    @Autowired
    private TMemberServiceImpl memberService;
    @RequestMapping("provider/get/memberpo/by/loginacct/remote")
    public ResultEntity<TMember> getMemberByLoginacctRemote(@RequestParam("loginAcct") String loginAcct){
        try {
            TMember member = memberService.getMemberByLoginacctRemote(loginAcct);
            return ResultEntity.successWithData(member);
        }catch (Exception e){
            return ResultEntity.failed("密码或账号不对，请确认重新输入");
        }
    }
    @RequestMapping("provider/save/memberpo/remote")
    ResultEntity<String> authPOMemberRegister(@RequestBody TMember memberPO){
        try {
            memberService.authPOMemberRegister(memberPO);
            return ResultEntity.successWithoutData();
        }catch (Exception e){
            e.printStackTrace();
            if (e instanceof DuplicateKeyException){
                return ResultEntity.failed(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
            return ResultEntity.failed(e.getMessage());
        }


    }
}

