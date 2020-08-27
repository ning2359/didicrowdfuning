package com.didi.crowd.service;

import com.didi.crowd.entrty.VO.DetailProjectVO;
import com.didi.crowd.entrty.VO.PortalTypeVO;
import com.didi.crowd.entrty.VO.ProjectVO;

import java.util.List;

public interface IProjectProviderService {
    void saveProjectVORemote(ProjectVO projectVO, Integer memberId);

    List<PortalTypeVO> getPortalTypeProjectDataRemote();

    DetailProjectVO getDetailProjectVORemote(Integer projectId);
}
