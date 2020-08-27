package com.didi.crowd.mapper;

import com.didi.crowd.entrty.PO.TProject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.didi.crowd.entrty.VO.DetailProjectVO;
import com.didi.crowd.entrty.VO.PortalTypeVO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author jobob
 * @since 2020-07-03
 */
@Component
public interface TProjectMapper extends BaseMapper<TProject> {

    List<PortalTypeVO> selectPortalTypeVOList();

    DetailProjectVO selectDetailProjectVO(Integer projectId);


}
