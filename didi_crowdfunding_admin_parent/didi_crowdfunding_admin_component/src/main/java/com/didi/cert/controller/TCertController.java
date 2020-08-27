package com.didi.cert.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.didi.cert.service.impl.TCertServiceImpl;
import com.didi.crowd.ResultEntity;
import com.didi.entrty.cert.TCert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jobob
 * @since 2020-07-24
 */
@Controller
public class TCertController {
    @Autowired
    private TCertServiceImpl certService;
    private Logger logger = LoggerFactory.getLogger(TCertController.class);
    @ResponseBody
    @RequestMapping("/cert/get/page.json")
    public ResultEntity<Page<TCert>> certDoIndex(
            @RequestParam(value = "current" ,defaultValue = "1") Integer current,
            @RequestParam(value = "pageSize" ,defaultValue = "5") Integer pageSize,
            @RequestParam(value = "keyWord",defaultValue = "")String keyWord
    ){
        try {
            Page<TCert> certPage = certService.getRolePage(current, pageSize, keyWord);
            return ResultEntity.successWithData(certPage);
        }catch (Exception e){
            e.printStackTrace();
            return ResultEntity.failed("查询失败"+e.getMessage());
        }
    }
    @ResponseBody
    @RequestMapping("/cert/save.json")
    public ResultEntity<String> saveCert(TCert cert){
        try {
            certService.saveCert(cert);
            return ResultEntity.successWithoutData();
        }catch (Exception e){
            e.printStackTrace();
            return ResultEntity.failed("新增失败"+e.getMessage());
        }
    }
    @ResponseBody
    @RequestMapping("/cert/update.json")
    public ResultEntity<String> updateCert(TCert cert){
        try {
            certService.updateCert(cert);
            return ResultEntity.successWithoutData();
        }catch (Exception e){
            e.printStackTrace();
            return ResultEntity.failed("新增失败"+e.getMessage());
        }
    }
    @ResponseBody
    @RequestMapping("/cert/delete/array.json")
    public ResultEntity<String> deleteCert(@RequestBody List<Integer> certIdList){
        try {
            certService.deleteCert(certIdList);
            return ResultEntity.successWithoutData();
        }catch (Exception e){
            e.printStackTrace();
            return ResultEntity.failed("新增失败"+e.getMessage());
        }
    }
    @RequestMapping("cert/to/assign.html")
    public String assignDoCertAssignAcct(Model model ){
        // 读取资质数据集合
        List<TCert> certs = certService.queryAll();
        logger.info("certs"+certs.toString());
        model.addAttribute("certs", certs);
        // 获取已经建立好的关系数据
        List<Map<String, Object>> maps = certService.queryAcctTypeCerts();//用于回显
        logger.info(maps.toString());
        model.addAttribute("certAccttypeList", maps);
        return "cert_assign";
    }

    @ResponseBody
    @RequestMapping("/certtype/insertAcctTypeCert.json")
    public ResultEntity<String> insertAcctTypeCert(
            @RequestParam("certid") Integer certid,
            @RequestParam("accttype") String accttype) {
        try {
                Map<String, Object> paramMap = new HashMap<String, Object>();
                paramMap.put("certid", certid);
                paramMap.put("accttype", accttype);
                certService.insertAcctTypeCert(paramMap);
               return ResultEntity.successWithoutData();
        } catch ( Exception e ) {
            e.printStackTrace();return ResultEntity.failed("保存失败"+e.getMessage());
        }
    }
       @ResponseBody
       @RequestMapping("/certtype/deleteAcctTypeCert.json")
       public ResultEntity<String> deleteAcctTypeCert(
            @RequestParam("certid") Integer certid,
            @RequestParam("accttype") String accttype
       ) {
            try {
                Map<String, Object> paramMap = new HashMap<String, Object>();
                paramMap.put("certid", certid);
                paramMap.put("accttype", accttype);
                certService.deleteAcctTypeCert(paramMap);
                return ResultEntity.successWithoutData();
            } catch (Exception e) {
                e.printStackTrace();
                return ResultEntity.failed("保存失败" + e.getMessage());
            }
    }
}

