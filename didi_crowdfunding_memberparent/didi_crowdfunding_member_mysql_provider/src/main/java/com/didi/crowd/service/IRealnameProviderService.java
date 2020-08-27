package com.didi.crowd.service;

import com.didi.crowd.entrty.PO.TCert;
import com.didi.crowd.entrty.PO.TMember;
import com.didi.crowd.entrty.VO.QualificationCert;
import com.didi.crowd.entrty.VO.RealNameVO;

import java.util.List;

public interface IRealnameProviderService {
    public List<TCert> queryCertsByAccttype(Integer accttype);

    void saveRealnameRemote(RealNameVO realName);

    TMember getMemberByMemberIdRemote(Integer memberId);


    void saveQualificationCert(Integer memberId, Integer certId, String quaPic);
}
