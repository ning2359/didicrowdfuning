package com.didi.role.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.didi.entrty.role.TRole;
import com.didi.role.mapper.TRoleMapper;
import com.didi.role.service.ITRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * @author jobob
 * @since 2020-06-10
 */
@Service
public class TRoleServiceImpl extends ServiceImpl<TRoleMapper, TRole> implements ITRoleService {
    @Autowired
    private TRoleMapper roleMapper;
    private Logger logger = LoggerFactory.getLogger(TRoleServiceImpl.class);
    @Override
    public Page<TRole> getRolePage(Integer current, Integer pageSize, String keyWord) {
        Integer totalCount = roleMapper.selectCount(null);
        Integer totalPage = totalCount/pageSize==0? totalCount/pageSize : totalCount/pageSize+1;
        logger.info("totalPage"+totalPage);
        if (current>=totalPage){
            current =totalPage;
        }
        Page<TRole> page = new Page<>(current,pageSize);
        QueryWrapper<TRole> wrapper = new QueryWrapper<>();
        wrapper.like(true,"name",keyWord);
        if (keyWord!=null && keyWord.length()>0){
            return   roleMapper.selectPage(page, wrapper);
        }else {
            return   roleMapper.selectPage(page, null);
        }
    }

    @Override
    public void saveRole(TRole role) {
        roleMapper.insert(role);
    }

    @Override
    public void updateRole(TRole role) {
       roleMapper.updateById(role);
    }

    @Override
    public void deleteRoleByArray(List<Integer> roleIds) {
        roleMapper.deleteBatchIds(roleIds);
    }

    @Override
    public List<TRole> getAssignedRole(Integer adminId) {
        return roleMapper.getAssignedRole(adminId);
    }

    @Override
    public List<TRole> getUnAssignedRole(Integer adminId) {
        return roleMapper.getUnAssignedRole(adminId);
    }

    @Override
    public void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIds) {
        roleMapper.deleteOldRelationship(adminId);
        if (roleIds !=null && roleIds.size()>0){
            roleMapper.insertNewRelationship(adminId , roleIds);
        }
    }
}
