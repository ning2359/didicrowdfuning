package com.didi.crowd.mapper;

import com.didi.crowd.entrty.PO.TOrderProject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.didi.crowd.entrty.VO.OrderProjectVO;
import org.springframework.stereotype.Component;

/**
 * @author jobob
 * @since 2020-07-08
 */
@Component
public interface TOrderProjectMapper extends BaseMapper<TOrderProject> {

    OrderProjectVO selectOrderProjectVO(Integer returnId);

}
