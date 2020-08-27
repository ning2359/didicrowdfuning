package com.didi.crowd.mapper;

import com.didi.crowd.entrty.PO.TOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
/**
 * @author jobob
 * @since 2020-07-08
 */
@Component
public interface TOrderMapper extends BaseMapper<TOrder> {

    void saveOderMember(@Param("orderPOId") Integer orderPOId, @Param("memberId") Integer memberId);
}
