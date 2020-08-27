package com.didi.crowd.controller;

import com.didi.crowd.ResultEntity;
import com.didi.crowd.entrty.PO.TCert;
import com.didi.crowd.entrty.PO.TMember;
import com.didi.crowd.entrty.VO.QualificationCert;
import com.didi.crowd.entrty.VO.RealNameVO;
import com.didi.crowd.service.impl.RealnameProviderServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RealnameProviderController {
    @Autowired
    private RealnameProviderServiceImpl realnameProviderService;

    private Logger logger = LoggerFactory.getLogger(RealnameProviderController.class);

    @RequestMapping("query/cert/by/accttypt")
    ResultEntity<List<TCert>> queryCertsByAccttype(
            @RequestParam("accttype") Integer accttype){
        try {
            logger.info("accttype"+accttype);
            List<TCert> certs = realnameProviderService.queryCertsByAccttype(accttype);
            logger.info(certs.toString());
            return ResultEntity.successWithData(certs);
        }catch (Exception e){
            e.printStackTrace();
            return ResultEntity.failed("查询错误"+e.getMessage());
        }
    }
    @RequestMapping("save/realname/remote")
    ResultEntity<String> saveRealnameRemote(@RequestBody RealNameVO realName){
        try {
            realnameProviderService.saveRealnameRemote(realName);
            return ResultEntity.successWithoutData();
        }catch (Exception e){
            return ResultEntity.failed("保存失败"+e.getMessage());
        }
    }
    @RequestMapping("get/member/by/memberid/remote")
    ResultEntity<TMember> getMemberByMemberIdRemote(@RequestParam("memberId") Integer memberId){
        try {
            logger.info("memberId"+memberId);
            TMember member= realnameProviderService.getMemberByMemberIdRemote(memberId);
            return ResultEntity.successWithData(member);
        }catch (Exception e){
            return ResultEntity.failed("保存失败"+e.getMessage());
        }
    }
    @RequestMapping("save/cert/pic")
    ResultEntity<String> saveQualificationCert(
            @RequestParam("memberId")Integer memberId,
            @RequestParam("certId")Integer certId,
            @RequestParam("quaPic")String quaPic){
        try {
             realnameProviderService.saveQualificationCert(memberId,certId,quaPic);
            return ResultEntity.successWithoutData();
        }catch (Exception e){
            return ResultEntity.failed("保存失败"+e.getMessage());
        }
    }
}
