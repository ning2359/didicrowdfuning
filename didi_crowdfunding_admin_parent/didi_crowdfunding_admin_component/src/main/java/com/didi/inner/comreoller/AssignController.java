package com.didi.inner.comreoller;

import com.didi.admin.controller.TAdminController;
import com.didi.entrty.role.TRole;
import com.didi.role.service.impl.TRoleServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
@Controller
public class AssignController {
    @Autowired
    private TRoleServiceImpl roleService;
    private Logger logger = LoggerFactory.getLogger(AssignController.class);
    @RequestMapping("/assign/to/assign/role/page.html")
    public String toAssignRolePage (  @RequestParam("adminId") Integer adminId,
                                      @RequestParam("current") Integer current,
                                      @RequestParam("text") String text,
                                      Map<String, Object> map
    ){
        List<TRole> assignedRole = roleService.getAssignedRole(adminId);
        List<TRole> unAssignedRole = roleService.getUnAssignedRole(adminId);
        map.put("assignedRole",assignedRole);
        map.put("unAssignedRole",unAssignedRole);
        return "assign_role";
    }
    @RequestMapping("/assign/do/role/assign.html")
    public String saveAdminRoleRelationship(
            @RequestParam("adminId") Integer adminId,
            @RequestParam("current") Integer current,
            @RequestParam("text") String text,
            @RequestParam(value = "roleIdList" ,required = false) List<Integer> roleIds
    ){
        logger.warn(String.valueOf(roleIds));
        roleService.saveAdminRoleRelationship(adminId,roleIds);
        return "redirect:/admin/get/page.html?current="+current+"&text="+text;
    }
}
