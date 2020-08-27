package com.didi.crowd.controller;

import com.didi.crowd.CrowdConstant;
import com.didi.crowd.ResultEntity;
import com.didi.crowd.api.MySqlRemoteService;
import com.didi.crowd.entrty.VO.PortalTypeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class PortalController {
    @Autowired
    private MySqlRemoteService mySqlRemoteService;

    @RequestMapping("/")
    public String shortPortalPage(Map<String,Object> map){
        ResultEntity<List<PortalTypeVO>> portalTypeProjectDataRemote = mySqlRemoteService.getPortalTypeProjectDataRemote();
        String result = portalTypeProjectDataRemote.getResult();
        if (ResultEntity.SUCCESS.equals(result)){
            List<PortalTypeVO> list = portalTypeProjectDataRemote.getData();
            // 4.存入模型
            map.put(CrowdConstant.ATTR_NAME_PORTAL_DATA, list);
        }
        return "portal";
    }
}
