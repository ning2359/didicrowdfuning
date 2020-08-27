package com.didi.menu.service;

import com.didi.entrty.menu.TMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.awt.*;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2020-06-12
 */
public interface ITMenuService extends IService<TMenu> {
    List<TMenu> getAll();

    void seaveMenu(TMenu menu);

    void updateMenu(TMenu menu);

    void deleteMenuById(Integer id);
}
