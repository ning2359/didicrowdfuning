package com.didi.crowd.mapper;

import com.didi.crowd.entrty.PO.TProjectItemPic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * @author jobob
 * @since 2020-07-03
 */
@Component
public interface TProjectItemPicMapper extends BaseMapper<TProjectItemPic> {

    void insertPathList(@Param("detailPicturePathList") List<String> detailPicturePathList, @Param("projectId") Integer projectId);
}
