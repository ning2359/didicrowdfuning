package com.didi.crowd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.didi.crowd.entrty.PO.TMember;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author jobob
 * @since 2020-06-29
 */
@Component
public interface TMemberMapper extends BaseMapper<TMember> {

    void insertProcessInstance(@Param("memberId") Integer memberId, @Param("procdefId") String procdefId);

    void insertQualification(@Param("memberId") Integer memberId, @Param("certId") Integer certId, @Param("quaPic") String quaPic);
}
