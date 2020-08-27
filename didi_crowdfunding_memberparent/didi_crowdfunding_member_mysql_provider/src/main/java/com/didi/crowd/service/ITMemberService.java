package com.didi.crowd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.didi.crowd.entrty.PO.TMember;
/**
 *
 * @author jobob
 * @since 2020-06-29
 */
public interface ITMemberService extends IService<TMember> {

    TMember getMemberByLoginacctRemote(String loginAcct);

    void authPOMemberRegister(TMember memberPO);
}
