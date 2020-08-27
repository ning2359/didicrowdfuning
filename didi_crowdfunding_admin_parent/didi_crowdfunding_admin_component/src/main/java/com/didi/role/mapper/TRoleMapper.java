package com.didi.role.mapper;
import com.didi.entrty.role.TRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * @author jobob
 * @since 2020-06-10
 */
public interface TRoleMapper extends BaseMapper<TRole> {

    List<TRole> getAssignedRole(Integer adminId);

    List<TRole> getUnAssignedRole(Integer adminId);

    void deleteOldRelationship(Integer adminId);

    void insertNewRelationship(@Param("adminId") Integer adminId,  @Param("roleIds")List<Integer> roleIds);

}
