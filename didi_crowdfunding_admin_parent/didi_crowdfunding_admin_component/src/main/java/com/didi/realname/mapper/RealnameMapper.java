package com.didi.realname.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.didi.crowd.entrty.PO.TMember;
import com.didi.entrty.utils.CertImg;
import com.didi.entrty.utils.Ticket;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RealnameMapper extends BaseMapper<TMember> {

    Ticket queryTicketByPiid(String processInstanceId);

    TMember queryMemberByMemberId(Integer memberId);

    List<CertImg>  queryCertImgsByMemberid(Integer memberId);
}
