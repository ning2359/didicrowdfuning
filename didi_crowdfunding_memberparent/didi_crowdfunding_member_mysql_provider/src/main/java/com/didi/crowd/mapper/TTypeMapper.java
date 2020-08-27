package com.didi.crowd.mapper;

import com.didi.crowd.entrty.PO.TType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * @author jobob
 * @since 2020-07-03
 */
@Component
public interface TTypeMapper extends BaseMapper<TType> {

    void insertTypeRelationship(@Param("projectId") Integer projectId, @Param("typeId") Integer typeId);
}
