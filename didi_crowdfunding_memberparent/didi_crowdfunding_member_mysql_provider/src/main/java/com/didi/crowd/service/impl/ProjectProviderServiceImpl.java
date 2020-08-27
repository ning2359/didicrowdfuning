package com.didi.crowd.service.impl;

import com.didi.crowd.entrty.PO.*;
import com.didi.crowd.entrty.VO.*;
import com.didi.crowd.controller.ProjectProviderController;
import com.didi.crowd.mapper.*;
import com.didi.crowd.service.IProjectProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProjectProviderServiceImpl implements IProjectProviderService {

    private Logger logger = LoggerFactory.getLogger(ProjectProviderController.class);

    @Autowired
    private TProjectMapper projectMapper;
    @Autowired
    private TReturnMapper returnMapper;
    @Autowired
    private TTypeMapper typeMapper;
    @Autowired
    private TTagMapper tagMapper;
    @Autowired
    private TProjectItemPicMapper projectItemPicMapper;
    @Autowired
    private TMemberConfirmInfoMapper memberConfirmInfoMapper;
    @Autowired
    private TMemberLaunchInfoMapper memberLaunchInfoMapper;
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void saveProjectVORemote(ProjectVO projectVO, Integer memberId) {
        // 一、保存ProjectPO对象
        TProject projectPO = new TProject();
        BeanUtils.copyProperties(projectVO,projectPO);
        projectPO.setStatus(0);
        projectPO.setMemberid(memberId);
        String createdate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        projectPO.setCreatedate(createdate);
        projectMapper.insert(projectPO);
        Integer projectId = projectPO.getId();
        logger.info("projectId:    "+projectId);

        // 二、保存项目、分类的关联关系信息

        Integer typeId = projectVO.getTypeId();
        logger.info("typeId"+typeId);
        typeMapper.insertTypeRelationship(projectId,typeId);
        // 三、保存项目、标签的关联关系信息
        List<String> tagNameList = projectVO.getTagNameList();
        logger.info("tagIdList"+tagNameList);
        List<Integer> tagIdList = new ArrayList<>();
        for (String tagName:tagNameList) {
            TTag tagPO = new TTag(projectId, tagName);
            tagMapper.insert(tagPO);
            Integer tagPOId = tagPO.getId();
            tagIdList.add(tagPOId);
        }


        // 四、保存项目、标签的关联关系信息
        logger.info("tagIdList"+tagIdList);
        tagMapper.insertTagRelationship(tagIdList,projectId);
        // 四、保存项目中详情图片路径信息
        List<String> detailPicturePathList = projectVO.getDetailPicturePathList();
        logger.info("detailPicturePathList"+detailPicturePathList);
        projectItemPicMapper.insertPathList(detailPicturePathList,projectId);

        // 五、保存项目发起人信息
        MemberLauchInfoVO memberLauchInfoVO = projectVO.getMemberLauchInfoVO();
        TMemberLaunchInfo memberLaunchInfoPO = new TMemberLaunchInfo();
        BeanUtils.copyProperties(memberLauchInfoVO,memberLaunchInfoPO);
        memberLaunchInfoPO.setMemberid(memberId);
        logger.info("memberLaunchInfoPO:    "+memberLaunchInfoPO);
        memberLaunchInfoMapper.insert(memberLaunchInfoPO);

        // 六、保存项目回报信息
        List<ReturnVO> returnVOList = projectVO.getReturnVOList();
        List<TReturn> returnPOList = new ArrayList<>();
        for (ReturnVO returnVO:returnVOList) {
            TReturn returnPO = new TReturn();
            BeanUtils.copyProperties(returnVO,returnPO);
            returnPOList.add(returnPO);
//            returnMapper.insert(returnPO);
        }
        logger.info("returnPOList"+returnPOList.toString());
        returnMapper.insertReturnPOBatch(projectId,returnPOList);
        // 七、保存项目确认信息
        MemberConfirmInfoVO memberConfirmInfoVO = projectVO.getMemberConfirmInfoVO();
        TMemberConfirmInfo memberConfirmInfoPO = new TMemberConfirmInfo();
        BeanUtils.copyProperties(memberConfirmInfoVO,memberConfirmInfoPO);
        memberConfirmInfoPO.setMemberid(memberId);
        logger.info("memberConfirmInfoPO"+memberConfirmInfoPO);
        memberConfirmInfoMapper.insert(memberConfirmInfoPO);

    }

    @Override
    public List<PortalTypeVO> getPortalTypeProjectDataRemote() {
        return projectMapper.selectPortalTypeVOList();

    }

    @Override
    public DetailProjectVO getDetailProjectVORemote(Integer projectId) {
        DetailProjectVO detailProjectVO = projectMapper.selectDetailProjectVO(projectId);
        List<DetailReturnVO> detailReturnVOList = detailProjectVO.getDetailReturnVOList();
        Integer supporterCount =0;
        for (DetailReturnVO detailReturnVO : detailReturnVOList) {
            Integer supproter = detailReturnVO.getSupproter();
            supporterCount+=supproter;
        }
        detailProjectVO.setSupporterCount(supporterCount);
        logger.info("detailProjectVO"+detailProjectVO.toString());
        Integer status = detailProjectVO.getStatus();
        String statusText = "";
        switch (status){
            case 0:
                statusText="审核中";
                break;
            case 1:
                statusText="众筹中";
                break;
            case 2:
                statusText="众筹成功";
                break;
            case 3:
                statusText="未开始";
                break;
            default:
                statusText="已关闭";
                break;
        }
        detailProjectVO.setStatusText(statusText);
        String deployDate = detailProjectVO.getDeployDate();
        Date currentDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date deployDay = format.parse(deployDate);
            long currentDateTime = currentDate.getTime();           //当前时间      大
            long deployDayTime = deployDay.getTime();               //项目发起时间  小
            long pastDays = (currentDateTime-deployDayTime)/1000/60/60/24;
            Integer totalDays = detailProjectVO.getDay();
            Integer lastDay = (int)(totalDays-pastDays);
            detailProjectVO.setLastDay(lastDay);
            logger.info("detailProjectVO"+detailProjectVO.toString());
            return detailProjectVO;
        } catch (ParseException e) {
            e.printStackTrace();
            return detailProjectVO;
        }

    }
}
