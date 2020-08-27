package com.didi.menu.controller;
import com.didi.crowd.ResultEntity;
import com.didi.entrty.menu.TMenu;
import com.didi.menu.service.impl.TMenuServiceImpl;
import com.didi.role.controller.TRoleController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jobob
 * @since 2020-06-12
 */
@Controller
public class TMenuController {
    @Autowired
    private TMenuServiceImpl menuService;
    private Logger logger = LoggerFactory.getLogger(TRoleController.class);
    @ResponseBody
    @RequestMapping("/menu/get/whole/tree.json")
    public ResultEntity<TMenu> getWholeTree(){
        List<TMenu> menus = menuService.getAll();
        TMenu root = null;
        Map<Integer,TMenu> map = new HashMap<>();
        for (TMenu menu:menus) {
            Integer id = menu.getId();
            map.put(id,menu);
        }
        for (TMenu menu :menus) {
            if (menu.getPid()==null){
                root = menu;
                continue;
            }
            TMenu father = map.get(menu.getPid());
            father.getChildren().add(menu);
        }
        return ResultEntity.successWithData(root);
    }
    @ResponseBody
    @RequestMapping("menu/save.json")
    public ResultEntity<String> saveMenu(TMenu menu){
        menuService.seaveMenu(menu);
        return ResultEntity.successWithoutData();
    }
    @ResponseBody
    @RequestMapping("menu/update.json")
    public ResultEntity<String> updateMenu(TMenu menu){
        menuService.updateMenu(menu);
        return ResultEntity.successWithoutData();
    }
    @ResponseBody
    @RequestMapping("menu/delete.json")
    public ResultEntity<String> deleteMent(@RequestParam("id") Integer id){
        menuService.deleteMenuById(id);
        return ResultEntity.successWithoutData();
    }
}

