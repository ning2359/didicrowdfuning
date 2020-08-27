package com.didi.cert.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.didi.cert.mapper.TCertMapper;
import com.didi.cert.service.ITCertService;
import com.didi.entrty.cert.TCert;
import com.didi.entrty.role.TRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
/**
 * @author jobob
 * @since 2020-07-24
 */
@Service
public class TCertServiceImpl extends ServiceImpl<TCertMapper, TCert> implements ITCertService {
    @Autowired
    private TCertMapper certMapper;
    private Logger logger  = LoggerFactory.getLogger(TCertServiceImpl.class);
    @Override
    public Page<TCert> getRolePage(Integer current, Integer pageSize, String keyWord) {
        Integer totalCount = certMapper.selectCount(null);
        Integer totalPage = totalCount/pageSize==0? totalCount/pageSize : totalCount/pageSize+1;
        logger.info("totalPage"+totalPage);
        if (current>=totalPage){
            current =totalPage;
        }
        Page<TCert> page = new Page<>(current,pageSize);
        QueryWrapper<TCert> wrapper = new QueryWrapper<>();
        wrapper.like(true,"name",keyWord);
        if (keyWord!=null && keyWord.length()>0){
            return   certMapper.selectPage(page, wrapper);
        }else {
            return   certMapper.selectPage(page, null);
        }
    }
    @Override
    public void saveCert(TCert cert) {
        certMapper.insert(cert);
    }

    @Override
    public void updateCert(TCert cert) {
    }

    @Override
    public void deleteCert(List<Integer> certIdList) {
        certMapper.deleteBatchIds(certIdList);
    }

    @Override
    public List<TCert> queryAll() {
        return certMapper.selectList(null);
    }

    @Override
    public List<Map<String, Object>> queryAcctTypeCerts() {
        return certMapper.queryAcctTypeCerts();
    }

    @Override
    public void insertAcctTypeCert(Map<String, Object> paramMap) {
        certMapper.insertAcctTypeCert(paramMap);
    }

    @Override
    public void deleteAcctTypeCert(Map<String, Object> paramMap) {
        certMapper.deleteAcctTypeCert(paramMap);
    }
}
