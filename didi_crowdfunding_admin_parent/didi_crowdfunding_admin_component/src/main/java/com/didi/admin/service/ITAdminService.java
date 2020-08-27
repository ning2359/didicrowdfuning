package com.didi.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.didi.entrty.admin.TAdmin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.didi.entrty.role.TRole;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2020-05-13
 */
public interface ITAdminService extends IService<TAdmin> {
    List<TAdmin> querryAdmin();
//    void severAdmin(TAdmin admin);
    TAdmin querryAdminByLoginAct(String loginAct,String userPwd);
    Page<TAdmin> querryAdminByPage(String text, int size, int current);
    void  remveAdmin(Integer id);
    void  saveAdmin(TAdmin admin);
    TAdmin  getAdminByid(Integer id);
    void editAdminBid(TAdmin admin);
    TAdmin getAdminByUserName(String rowUserName);
}
