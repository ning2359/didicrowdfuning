package com.didi.crowd.controller;

import com.didi.crowd.ResultEntity;
import com.didi.crowd.entrty.VO.DetailProjectVO;
import com.didi.crowd.entrty.VO.PortalTypeVO;
import com.didi.crowd.entrty.VO.ProjectVO;
import com.didi.crowd.service.impl.ProjectProviderServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProjectProviderController {

    private Logger logger = LoggerFactory.getLogger(ProjectProviderController.class);

    @Autowired
    private ProjectProviderServiceImpl projectProviderService;

    @RequestMapping("/get/project/detail/remote")
    public ResultEntity<DetailProjectVO> getDetailProjectVORemote(@RequestParam("projectId") Integer projectId){
        try {
            DetailProjectVO detailProjectVO=  projectProviderService.getDetailProjectVORemote(projectId);
            return ResultEntity.successWithData(detailProjectVO);
        }catch (Exception e){
            e.printStackTrace();
            return ResultEntity.failed("查询详情页失败"+e.getMessage());
        }
    }

    @RequestMapping("/get/portal/type/project/data/remote")
    public ResultEntity<List<PortalTypeVO>> getPortalTypeProjectDataRemote(){
        try {
            List<PortalTypeVO> typeProjectDataRemote = projectProviderService.getPortalTypeProjectDataRemote();
            return ResultEntity.successWithData(typeProjectDataRemote);
        }catch (Exception e){
            e.printStackTrace();
            return ResultEntity.failed("查询失败,请规范查找 再试一次"+e.getMessage());
        }
    }

    @RequestMapping("/save/project/vo/remote")
    ResultEntity<String> saveProjectVORemote(
            @RequestBody ProjectVO projectVO,
            @RequestParam("memberId") Integer memberId
        ){
        try {
            logger.info("projectVO:    "+(projectVO));
            projectProviderService.saveProjectVORemote(projectVO,memberId);
            return ResultEntity.successWithoutData();
        }catch (Exception e){
            e.printStackTrace();
            return ResultEntity.failed("保存失败 ，请再试一次"+e.getMessage());
        }
    }
}
