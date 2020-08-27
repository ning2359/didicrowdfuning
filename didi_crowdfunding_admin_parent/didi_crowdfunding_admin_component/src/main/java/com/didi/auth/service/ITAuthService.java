package com.didi.auth.service;
import com.didi.entrty.auth.TAuth;
import com.baomidou.mybatisplus.extension.service.IService;
import com.didi.entrty.role.TRole;

import java.util.List;
import java.util.Map;

/**
 * @author jobob
 * @since 2020-06-14
 */
public interface ITAuthService extends IService<TAuth> {
    List<TAuth> getAllAuth();
    List<Integer> getAssignedAuthByRoleId(Integer roleId);
    void saveRoleAssignAuth(Map<String, List<Integer>> map);
    List<String> getAssignedAuthNameByAdminId(Integer adminId);
}
