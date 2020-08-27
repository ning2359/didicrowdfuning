package com.didi.crowd.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.didi.crowd.entrty.PO.TMember;
import com.didi.crowd.mapper.TMemberMapper;
import com.didi.crowd.service.ITMemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * @author jobob
 * @since 2020-06-29
 */
@Transactional(readOnly = true)
@Service
public class TMemberServiceImpl extends ServiceImpl<TMemberMapper, TMember> implements ITMemberService {
    private Logger logger = LoggerFactory.getLogger(TMemberServiceImpl.class);
    @Autowired
    private TMemberMapper memberMapper;
    public TMember getMemberByLoginacctRemote(String loginAcct) {
        QueryWrapper<TMember> wrapper = new QueryWrapper<TMember>();
        wrapper.eq(true,"login_acct",loginAcct);
        List<TMember> tMembers = memberMapper.selectList(wrapper);
        return tMembers.get(0);

    }
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class,readOnly = false)
    @Override
    public void authPOMemberRegister(TMember memberPO) {
        memberMapper.insert(memberPO);
    }
}
