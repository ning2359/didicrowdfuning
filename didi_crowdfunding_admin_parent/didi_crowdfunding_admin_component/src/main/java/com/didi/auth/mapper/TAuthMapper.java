package com.didi.auth.mapper;
import com.didi.entrty.auth.TAuth;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author jobob
 * @since 2020-06-14
 */
public interface TAuthMapper extends BaseMapper<TAuth> {

    List<Integer> getAssignedAuthByRoleId(Integer roleId);

    void insertRoleAssignAuth(@Param("roleId") Integer roleId, @Param("authIds") List<Integer> authIds);

    void deleteAuthRelationship(Integer roleId);

    List<String> getAssignedAuthNameByAdminId(Integer adminId);
}
