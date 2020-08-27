package com.didi.cert.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.didi.entrty.cert.TCert;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
/**
 * @author jobob
 * @since 2020-07-24
 */
@Component
public interface TCertMapper extends BaseMapper<TCert> {

    List<Map<String, Object>> queryAcctTypeCerts();

    void insertAcctTypeCert(Map<String, Object> paramMap);

    void deleteAcctTypeCert(Map<String, Object> paramMap);
}
