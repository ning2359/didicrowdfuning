package com.didi.auth.service.impl;
import com.didi.admin.controller.TAdminController;
import com.didi.entrty.auth.TAuth;
import com.didi.auth.mapper.TAuthMapper;
import com.didi.auth.service.ITAuthService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.didi.entrty.role.TRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author jobob
 * @since 2020-06-14
 */
@Service
public class TAuthServiceImpl extends ServiceImpl<TAuthMapper, TAuth> implements ITAuthService {
    @Autowired
    private TAuthMapper authMapper;
    private Logger logger = LoggerFactory.getLogger(TAdminController.class);

    @Override
    public List<TAuth> getAllAuth() {
        return authMapper.selectList(null);
    }

    @Override
    public List<Integer> getAssignedAuthByRoleId(Integer roleId) {
        return authMapper.getAssignedAuthByRoleId(roleId);
    }

    @Override
    public void saveRoleAssignAuth(Map<String, List<Integer>> map) {
        Integer roleId = map.get("roleId").get(0);
        List<Integer> authIds = map.get("authIdArray");
        logger.warn(String.valueOf(authIds));
        logger.warn(String.valueOf(roleId));
        authMapper.deleteAuthRelationship(roleId);
        if (authIds !=null && authIds.size()>0){
            authMapper.insertRoleAssignAuth(roleId,authIds);
        }
    }

    @Override
    public List<String> getAssignedAuthNameByAdminId(Integer adminId) {
       return authMapper.getAssignedAuthNameByAdminId(adminId);
    }


}
