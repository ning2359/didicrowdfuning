package com.didi.menu.service.impl;
import com.didi.entrty.menu.TMenu;
import com.didi.menu.mapper.TMenuMapper;
import com.didi.menu.service.ITMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * @author jobob
 * @since 2020-06-12
 */
@Service
public class TMenuServiceImpl extends ServiceImpl<TMenuMapper, TMenu> implements ITMenuService {
    @Autowired
    private TMenuMapper menuMapper;
    @Override
    public List<TMenu> getAll() {
        return menuMapper.selectList(null);
    }

    @Override
    public void seaveMenu(TMenu menu) {
        menuMapper.insert(menu);
    }

    @Override
    public void updateMenu(TMenu menu) {
        menuMapper.updateById(menu);
    }

    @Override
    public void deleteMenuById(Integer id) {
        menuMapper.deleteById(id);
    }
}
