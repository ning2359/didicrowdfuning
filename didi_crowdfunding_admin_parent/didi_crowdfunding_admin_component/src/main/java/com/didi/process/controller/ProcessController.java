package com.didi.process.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.didi.crowd.ResultEntity;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProcessController {
    @Autowired
    private RepositoryService repositoryService;
    private Logger logger = LoggerFactory.getLogger(ProcessController.class);

    @ResponseBody
    @RequestMapping("/process/deploy.json")
    public ResultEntity< Page<Map<String, Object>>> processDeploy(@RequestParam(value = "current" ,defaultValue = "1") Integer current,
                                                                  @RequestParam(value = "pageSize" ,defaultValue = "5") Integer pageSize,
                                                                  @RequestParam(value = "keyWord",defaultValue = "")String keyWord,
                                                                  Map<String,Object> map
                                                                ){
        logger.info("当前页"+current);
        logger.info("页面大小"+pageSize);
        logger.info("查询关键字"+keyWord);
        try {
            // 查询流程定义数据
            ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery(); //repositoryService对象可以采用自动装配
            List<ProcessDefinition> pds = query.listPage((current-1)*pageSize, pageSize);
            logger.info(pds.toString());
//             使用Map来代替流程定义对象
            List<Map<String, Object>> pdMaps = new ArrayList<Map<String, Object>>(); //圈圈
            for ( ProcessDefinition pd : pds ) {
                Map<String, Object> pdMap = new HashMap<String, Object>();
                pdMap.put("id", pd.getId());
                pdMap.put("name", pd.getName());
                pdMap.put("key", pd.getKey());
                pdMap.put("version", pd.getVersion());
                pdMaps.add(pdMap);
            }
            int count = (int)query.count();
            Page<Map<String, Object>> page = new Page<Map<String, Object>>(current,pageSize);
            page.setRecords(pdMaps);
            page.setTotal(count );
            page.setSize(pageSize);
            page.setCurrent(current);
            map.put("page",page);
            logger.info("page"+page);
            return ResultEntity.successWithData(page);
        } catch ( Exception e ) {
            e.printStackTrace();
            return ResultEntity.failed("查询错误，"+e.getMessage());
        }
    }
    @ResponseBody
    @RequestMapping("/process/upload.json")
    public ResultEntity<String> processUpload(
            @RequestParam("bpmnFile") MultipartFile bpmnFile,
            @RequestParam("viewFile") MultipartFile viewFile
        ){
        try {
            logger.info("bpmnFile"+bpmnFile.getOriginalFilename());
            logger.info("viewFile"+viewFile.getOriginalFilename());
            // 部署流程定义文件
            Deployment deploy = repositoryService.createDeployment()
                    //.addClasspathResource(file.getOriginalFilename())
                    .addInputStream(bpmnFile.getOriginalFilename(), bpmnFile.getInputStream())
                    .addInputStream(viewFile.getOriginalFilename(), viewFile.getInputStream())
                    .deploy();
            logger.info("部署Id"+deploy.getId());
            logger.info("部署Name"+deploy.getName());
            return ResultEntity.successWithoutData();
        } catch ( Exception e ) {
            e.printStackTrace();
          return ResultEntity.failed("部署失败"+e.getMessage());
        }
    }
    @ResponseBody
    @RequestMapping("/process/delete/array.json")
    public ResultEntity<String> processDeleteArray(
            @RequestBody List<String> processKeyArray
    ){
        try {
            // 流程定义ID ==> 流程定义对象 ==》 部署ID
            // 查询流程定义
            for (int i = 0;i<processKeyArray.size();i++){
                String processId = processKeyArray.get(i);
                ProcessDefinitionQuery processDefinitionQuery =repositoryService.createProcessDefinitionQuery();
                ProcessDefinition processDefinition = processDefinitionQuery.processDefinitionId(processId).singleResult();
                logger.info("processDefinition的id属性"+processDefinition.getId());
                // 删除流程定义(级联)
                repositoryService.deleteDeployment(processDefinition.getDeploymentId(), true);
            }

           return ResultEntity.successWithoutData();
        } catch ( Exception e ) {
            e.printStackTrace();
           return ResultEntity.failed("删除流程失败"+e.getMessage());
        }
    }
    @RequestMapping("/process/show/view.html")
    public void processShowView(
        @RequestParam("processId") String processId,
        HttpServletResponse response
    ) throws IOException {
        logger.info("processId"+processId);
        //根据主键查询流程定义
        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionId(processId)
                .singleResult();
        logger.info("processDefinition的Id值"+processDefinition.getId());
        //读取流程定义图片
        InputStream in = repositoryService.getResourceAsStream(
                processDefinition.getDeploymentId(),
                processDefinition.getDiagramResourceName());
        OutputStream out = response.getOutputStream();
        //将流程定义图片输出给客户端
        int i = -1 ;
        while(( i = in.read() ) != -1){
            out.write(i);
        }
        //IOUtils.copy(in, out);


    }

}
