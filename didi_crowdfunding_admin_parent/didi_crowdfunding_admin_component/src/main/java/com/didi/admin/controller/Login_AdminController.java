package com.didi.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.didi.crowd.ResultEntity;
import com.didi.entrty.admin.TAdmin;
import com.didi.crowd.CrowdConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.didi.admin.service.impl.TAdminServiceImpl;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Objects;

@Controller
@SessionAttributes(types = TAdmin.class)
public class Login_AdminController {
    private Logger logger = LoggerFactory.getLogger(TAdminController.class);
    @Autowired
    private TAdminServiceImpl adminService;


    @RequestMapping("/admin/get/page.html")
    public String queryAdminByPage(@RequestParam(value = "text" ,defaultValue = "") String text,
                                         @RequestParam(value = "current",defaultValue = "1") int current,
                                         @RequestParam(value = "size",defaultValue = "10") int size,
                                         Map<String,Object> map
                                         ){
        Page<TAdmin> page = adminService.querryAdminByPage(text, size, current);
       map.put(CrowdConstant.PAGE,page);
        return "admin_page";
    }
    @PreAuthorize("hasRole('经理')")
    @RequestMapping("admin/remove/{id}/{current}/{text}.html")
    public String remove(
            @PathVariable("id") Integer id,
            @PathVariable("current") Integer current,
            @PathVariable("text") String text
                    ){
        adminService.remveAdmin(id);
        return "redirect:/admin/get/page.html?current="+current+"&text="+text;
    }
    @PreAuthorize("hasRole('经理')")
    @RequestMapping("/admin/save.html")
    public String saveAdmin(TAdmin admin){
        adminService.saveAdmin(admin);
        return "redirect:/admin/get/page.html?current="+Integer.MAX_VALUE;
    }
    @PreAuthorize("hasRole('ROLE_经理')")
    @RequestMapping("admin/getAdminById.html")
    public String getAdminById(@RequestParam("id") Integer id,Map<String,Object> map){
        TAdmin admin = adminService.getAdminByid(id);
        map.put(CrowdConstant.ADMIN,admin);
        return "admin_edit";
    }
    @PreAuthorize("hasRole('经理')")
    @RequestMapping("/admin/edit.html")
    public String editAdminByid(@RequestParam("id") Integer id,TAdmin tAdmin){
        adminService.editAdminBid(tAdmin);
        return "redirect:/admin/get/page.html";
    }
     /*
    @RequestMapping("/admin/do/login.html")
    public  String loginAdmin(@RequestParam("loginAcct") String loginAcct, @RequestParam("userPswd") String uerPwd , Map<String,Object> map){
        TAdmin tAdmin = adminService.querryAdminByLoginAct(loginAcct, uerPwd);
        map.put(CrowdConstant.ADMIN,tAdmin);
        return "redirect:/admin/to/main/page.html";
    }
    @RequestMapping("/admin/do/logout.html")
    public String quitAdmin(HttpSession session){
            session.invalidate();
        return "redirect:redirect:/admin/to/main/page.html";
    }

     */

}
