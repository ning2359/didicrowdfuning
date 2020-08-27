package com.didi.crowd.service.impl;


import com.didi.crowd.entrty.PO.TCert;
import com.didi.crowd.entrty.PO.TMember;
import com.didi.crowd.entrty.VO.QualificationCert;
import com.didi.crowd.entrty.VO.RealNameVO;
import com.didi.crowd.mapper.TCertMapper;
import com.didi.crowd.mapper.TMemberMapper;
import com.didi.crowd.service.IRealnameProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RealnameProviderServiceImpl implements IRealnameProviderService {
    @Autowired
    private TCertMapper certMapper;
    @Autowired
    private TMemberMapper memberMapper;

    private Logger logger = LoggerFactory.getLogger(RealnameProviderServiceImpl.class);

    public List<TCert> queryCertsByAccttype(Integer accttype) {
       return  certMapper.queryCertsByAccttype(accttype);
    }

    @Override
    public void saveRealnameRemote(RealNameVO realName) {
        TMember member = new TMember();
        BeanUtils.copyProperties(realName,member);
        logger.info("member"+member);
        member.setAuthstatus(1);
        Integer memberId = realName.getMemberId();
        member.setId(memberId);
        List<String> qualificationpctureList = realName.getQualificationpctureList();
        memberMapper.updateById(member);

        //保存流程实例Id
        String procdefId = realName.getProcdefId();
        memberMapper.insertProcessInstance(memberId,procdefId);
    }

    @Override
    public TMember getMemberByMemberIdRemote(Integer memberId) {

        TMember member = memberMapper.selectById(memberId);
        logger.info("member"+member);
        return member;
    }

    @Override
    public void saveQualificationCert(Integer memberId, Integer certId, String quaPic) {
        memberMapper.insertQualification(memberId,certId,quaPic);
    }


}
