package com.didi.realname.service;

import com.didi.crowd.entrty.PO.TMember;
import com.didi.realname.mapper.RealnameMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RealnameService {

    @Autowired
    private RealnameMapper realnameMapper;
    private Logger logger = LoggerFactory.getLogger(RealnameService.class);
    public void passRequest(Integer memberId){
        TMember member = realnameMapper.selectById(memberId);
        logger.info(member.toString());
        member.setAuthstatus(2);
        realnameMapper.updateById(member);
    }

    public void  rejectRequest(Integer memberId){
        TMember member = realnameMapper.selectById(memberId);
        logger.info(member.toString());
        member.setAuthstatus(0);
        realnameMapper.updateById(member);
    }
}
