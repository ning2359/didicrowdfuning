package com.didi.crowd.mapper;

import com.didi.crowd.entrty.PO.TTag;
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
public interface TTagMapper extends BaseMapper<TTag> {

    void insertTagRelationship(@Param("tagIdList") List<Integer> tagIdList, @Param("projectId") Integer projectId);

}
