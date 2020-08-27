package com.didi.cert.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.didi.entrty.cert.TCert;

import java.util.List;
import java.util.Map;

/**
 * @author jobob
 * @since 2020-07-24
 */
public interface ITCertService extends IService<TCert> {

    Page<TCert> getRolePage(Integer current, Integer pageSize, String keyWord);

    void saveCert(TCert cert);

    void updateCert(TCert cert);

    void deleteCert(List<Integer> certIdList);

    List<TCert> queryAll();

    List<Map<String, Object>> queryAcctTypeCerts();

    void insertAcctTypeCert(Map<String, Object> paramMap);

    void deleteAcctTypeCert(Map<String, Object> paramMap);
}
