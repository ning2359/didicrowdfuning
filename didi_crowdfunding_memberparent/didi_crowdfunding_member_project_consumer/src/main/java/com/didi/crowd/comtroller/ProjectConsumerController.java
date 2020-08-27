package com.didi.crowd.comtroller;

import com.didi.crowd.CrowdConstant;
import com.didi.crowd.CrowdUtil;
import com.didi.crowd.ResultEntity;
import com.didi.crowd.api.MySqlRemoteService;
import com.didi.crowd.config.OSSProperties;
import com.didi.crowd.entrty.VO.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class ProjectConsumerController {
    @Autowired
    private OSSProperties properties;

    @Autowired
    private MySqlRemoteService mySqlRemoteService;

    private Logger logger = LoggerFactory.getLogger(ProjectConsumerController.class);
    @RequestMapping("/get/project/detail")
    public String getProjectDetail(@RequestParam("projectId")Integer projectId,
                                   Map<String,Object> map){
        ResultEntity<DetailProjectVO> detailProjectVORemote = mySqlRemoteService.getDetailProjectVORemote(projectId);
        String result = detailProjectVORemote.getResult();
        if (ResultEntity.SUCCESS.equals(result)){
            DetailProjectVO detailProjectVO = detailProjectVORemote.getData();
            map.put(CrowdConstant.ATTR_NAME_DETAIL_PROJECT, detailProjectVO);
            logger.info("detailProjectVO"+detailProjectVO.toString());
        }
        return "project_show_detail";
    }
    @RequestMapping("/create/confirm")
    public String createConfirmPage(
            MemberConfirmInfoVO confirmInfoVO,
            HttpSession session,
            Map<String,String> map
    ){
        ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);
        if (projectVO==null){
            map.put(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_TEMPLE_PROJECT_MISSING);
            return "project_launch";
        }
        projectVO.setMemberConfirmInfoVO(confirmInfoVO);
        LoginMemberVO loginMemberVO = (LoginMemberVO) session.getAttribute(CrowdConstant.MEMBER);
        Integer memberId = loginMemberVO.getId();
        ResultEntity<String> saveResultEntity = mySqlRemoteService.saveProjectVORemote(projectVO, memberId);
        String result = saveResultEntity.getResult();
        if (ResultEntity.FAILED.equals(result)){
            map.put(CrowdConstant.ATTR_NAME_MESSAGE,saveResultEntity.getMessage());
            return "project_launch";
        }
       session.removeAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);
        return "redirect:http://localhost:80/project/create/success";

    }

    @ResponseBody
    @RequestMapping("/create/upload/return/picture.json")
    public ResultEntity<String> createUploadReturnPicture(
           @RequestParam("returnPicture") MultipartFile returnPicture
            ) throws IOException {
        ResultEntity<String> uploadReturnPicResultEntity = CrowdUtil.uploadFileToOss(
                properties.getEndPoint(),
                properties.getAccessKeyId(),
                properties.getAccessKeySecret(),
                returnPicture.getInputStream(),
                properties.getBucketName(),
                properties.getBucketDomain(),
                returnPicture.getOriginalFilename()
        );

        return uploadReturnPicResultEntity;
    }

    @ResponseBody
    @RequestMapping("/create/save/return.json")
    public ResultEntity<String> saveReturn(ReturnVO returnVO, HttpSession session) {
        try {
            ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);
            if (projectVO==null){
                return ResultEntity.failed(CrowdConstant.MESSAGE_TEMPLE_PROJECT_MISSING);
            }
            List<ReturnVO> returnVOList = projectVO.getReturnVOList();
            if (returnVOList==null || returnVOList.size()==0){
                returnVOList = new ArrayList<>();
            }
            returnVOList.add(returnVO);
            projectVO.setReturnVOList(returnVOList);
            session.setAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT,projectVO);
            return ResultEntity.successWithoutData();
        }catch (Exception e){
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    @RequestMapping("/create/project/information")
    public String createProjectInformation(
            ProjectVO projectVO,
            MultipartFile headerPicture,
            List<MultipartFile> detailPictureList,
            HttpSession session,
            Map<String,String> map
        ) throws IOException {
        logger.info("projectVO"+projectVO.toString());
        logger.info("properties"+properties.toString());
        boolean empty = headerPicture.isEmpty();
        if (empty){
            map.put(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_HEADER_PIC_EMPTY);
            return "project_launch";
        }
        ResultEntity<String> uploadHeaderPicResultEntity = CrowdUtil.uploadFileToOss(
                properties.getEndPoint(),
                properties.getAccessKeyId(),
                properties.getAccessKeySecret(),
                headerPicture.getInputStream(),
                properties.getBucketName(),
                properties.getBucketDomain(),
                headerPicture.getOriginalFilename()
        );
        String result = uploadHeaderPicResultEntity.getResult();
        if (ResultEntity.FAILED.equals(result)){
            map.put(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_PIC_UPLOAD_FAILED);
            return "project_launch";
        }
        String data = uploadHeaderPicResultEntity.getData();
        projectVO.setHeaderPicturePath(data);
        List<String> detailPicturePathList = new ArrayList<>();
        if (detailPictureList==null&&detailPictureList.size()>0){
            map.put(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_DETAIL_PIC_EMPTY);
            return "project_launch";
        }
        for (MultipartFile detailPicture :detailPictureList) {
            if (detailPicture.isEmpty()){
                map.put(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_DETAIL_PIC_EMPTY);
                return "project_launch";
            }
            ResultEntity<String> uploadDetailPictureList = CrowdUtil.uploadFileToOss(
                    properties.getEndPoint(),
                    properties.getAccessKeyId(),
                    properties.getAccessKeySecret(),
                    detailPicture.getInputStream(),
                    properties.getBucketName(),
                    properties.getBucketDomain(),
                    detailPicture.getOriginalFilename()
            );
            String detailResult = uploadDetailPictureList.getResult();
            if (ResultEntity.FAILED.equals(detailResult)){
                map.put(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_PIC_UPLOAD_FAILED);
                return "project_launch";
            }
            String detailData = uploadDetailPictureList.getData();
            detailPicturePathList.add(detailData);
        }
        projectVO.setDetailPicturePathList(detailPicturePathList);
        session.setAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT,projectVO);
        return "redirect:http://localhost:80/project/return/info/page";
    }

}
