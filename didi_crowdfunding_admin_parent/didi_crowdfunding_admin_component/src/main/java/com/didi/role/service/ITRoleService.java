package com.didi.role.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.didi.entrty.role.TRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2020-06-10
 */
public interface ITRoleService extends IService<TRole> {

    Page<TRole> getRolePage(Integer current, Integer pageSize, String keyWord);

    void saveRole(TRole role);

    void updateRole(TRole role);

    void deleteRoleByArray(List<Integer> roleIds);

    List<TRole> getAssignedRole(Integer adminId);

    List<TRole> getUnAssignedRole(Integer adminId);

    void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIds);
}
