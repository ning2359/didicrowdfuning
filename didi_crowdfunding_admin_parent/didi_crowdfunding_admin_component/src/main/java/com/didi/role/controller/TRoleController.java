package com.didi.role.controller;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.didi.admin.controller.TAdminController;
import com.didi.crowd.CrowdConstant;
import com.didi.crowd.ResultEntity;
import com.didi.entrty.role.TRole;
import com.didi.role.service.impl.TRoleServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jobob
 * @since 2020-06-10
 */
@Controller
public class TRoleController {
    @Autowired
    private TRoleServiceImpl roleService;
    private Logger logger = LoggerFactory.getLogger(TRoleController.class);
    @PreAuthorize("hasRole('部长') OR hasAuthority('role:get')")
    @RequestMapping("/to/role/get/page.json")
    @ResponseBody
    public ResultEntity<Page<TRole>> getRolePage(@RequestParam(value = "current" ,defaultValue = "1") Integer current,
                                    @RequestParam(value = "pageSize" ,defaultValue = "5") Integer pageSize,
                                    @RequestParam(value = "keyWord",defaultValue = "")String keyWord){
        Page<TRole> rolePage = roleService.getRolePage(current, pageSize, keyWord);
        return ResultEntity.successWithData(rolePage);
    }
    @PreAuthorize("hasRole('部长')")
    @ResponseBody
    @RequestMapping("/role/save.json")
    public ResultEntity<String> saveRole(TRole role){
        roleService.saveRole(role);
        return ResultEntity.successWithoutData();
    }
    @PreAuthorize("hasRole('部长')")
    @ResponseBody
    @RequestMapping("/role/update.json")
    public ResultEntity<String> updateRole(TRole role){
        logger.warn(String.valueOf(role));
        roleService.updateRole(role);
        return ResultEntity.successWithoutData();
    }
    @PreAuthorize("hasRole('部长')")
    @ResponseBody
    @RequestMapping("/role/delete/array.json")
    public ResultEntity<String> deleteRoleByArray(@RequestBody List<Integer> roleIds){
        roleService.deleteRoleByArray(roleIds);
        return ResultEntity.successWithoutData();
    }
}

