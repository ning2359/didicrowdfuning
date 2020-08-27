package com.didi.admin.mapper;

import com.didi.entrty.admin.TAdmin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


public interface TAdminMapper extends BaseMapper<TAdmin> {
    List<TAdmin> querry();
}
