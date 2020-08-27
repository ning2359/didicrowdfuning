package com.didi.auth.controller;
import com.didi.auth.service.impl.TAuthServiceImpl;
import com.didi.crowd.ResultEntity;
import com.didi.entrty.auth.TAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author jobob
 * @since 2020-06-14
 */
@Controller
public class TAuthController {
    @Autowired
    private TAuthServiceImpl authService;
    @ResponseBody
    @RequestMapping("/assign/get/all/auth.json")
    public ResultEntity<List<TAuth>> getAllAuth(){
        List<TAuth> allAuth = authService.getAllAuth();
        return ResultEntity.successWithData(allAuth);
    }
    @ResponseBody
    @RequestMapping("/assign/get/assigned/auth/id/by/role/id.json")
    public ResultEntity<List<Integer>> getAssignedAuthByRoleId(@RequestParam("roleId") Integer roleId){
        List<Integer> authIds = authService.getAssignedAuthByRoleId(roleId);
        return ResultEntity.successWithData(authIds);
    }
    @ResponseBody
    @RequestMapping("assign/do/role/assign/auth.json")
    public ResultEntity<String> saveRoleAssignAuth(@RequestBody Map<String,List<Integer>> map ){
        authService.saveRoleAssignAuth(map);
        return ResultEntity.successWithoutData();
    }
}

