package com.didi.crowd.mapper;

import com.didi.crowd.entrty.PO.TReturn;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.didi.crowd.entrty.VO.ReturnVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author jobob
 * @since 2020-07-03
 */
@Component
public interface TReturnMapper extends BaseMapper<TReturn> {

    void insertReturnPOBatch(@Param("projectId") Integer projectId, @Param("returnPOList") List<TReturn> returnPOList);
}
