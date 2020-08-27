package com.didi.admin.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.didi.entrty.admin.TAdmin;
import com.didi.admin.controller.TAdminController;
import com.didi.admin.service.ITAdminService;
import com.didi.admin.mapper.TAdminMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.didi.crowd.CrowdConstant;
import com.didi.encrypd.MD5Encrypted;
import com.didi.exception.AcctKeyException;
import com.didi.exception.LoginFailedException;
import com.didi.exception.UpdateAcctRepeatExcepyion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
@Service
public class TAdminServiceImpl extends ServiceImpl<TAdminMapper, TAdmin> implements ITAdminService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private Logger logger = LoggerFactory.getLogger(TAdminController.class);
    @Autowired
    private TAdminMapper mapper;

    public List<TAdmin> querryAdmin() {
        return mapper.selectList(null);
    }

//    public void severAdmin(TAdmin admin) {
//        mapper.insert(admin);
//
////        throw  new RuntimeException();
//    }

    public TAdmin querryAdminByLoginAct(String loginAcct, String userPwd) {
        QueryWrapper<TAdmin> wrapper = new QueryWrapper<TAdmin>();
        wrapper.eq(true,"login_acct",loginAcct);
        List<TAdmin> Admins = mapper.selectList(wrapper);
//        账户不存在
        if (Admins==null || Admins.size()==0){
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
//        账号存在多个
        if (Admins.size()>1){
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_SYSTEM_EXCEPTION);
        }
        TAdmin admin = Admins.get(0);
        if (admin==null){
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
//    登陆密码加密
        String md5 = MD5Encrypted.md5(userPwd);
        if (!(Objects.equals(admin.getUserPswd(),md5))){
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_PWD);
        }
        return admin;
    }

    @Override
    public Page<TAdmin> querryAdminByPage(String text, int size, int current) {
        QueryWrapper<TAdmin> wrapper = new QueryWrapper<>();
        wrapper.like(true,"user_name",text)
                .or(true)
                .like(true,"login_acct",text)
                .or(true)
                .like(true,"email",text)
        ;
        Integer totalCount = mapper.selectCount(null);
        Integer totalPage = totalCount/size==0? totalCount/size : totalCount/size+1;
        logger.info("totalPage"+totalPage);
        if (current>=totalPage){
              current =totalPage;
        }
        if ("".equals(text)){
            Page<TAdmin> page = mapper.selectPage(new Page<TAdmin>(current, size), null);
            return page;
        }else {
            Page<TAdmin> page = mapper.selectPage(new Page<TAdmin>(current, size), wrapper);
            return page;
        }
    }
    @Override
    public void remveAdmin(Integer id) {
         mapper.deleteById(id);
    }

    @Override
    public void saveAdmin(TAdmin admin) {
        //对用户密码进行盐值加密
        String userPswd = admin.getUserPswd();
        // userPswd= MD5Encrypted.md5(userPswd);
        String encode = bCryptPasswordEncoder.encode(userPswd);
        admin.setUserPswd(encode);
        //获取当前时间 作为建号时间
        Date date =new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = dateFormat.format(date);
        admin.setCreateTime(createTime);
        logger.debug(String.valueOf(admin));
        try {
            mapper.insert(admin);
        }catch (Exception e){
            e.printStackTrace();
            if (e instanceof DuplicateKeyException){
                throw  new AcctKeyException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
    }

    @Override
    public TAdmin getAdminByid(Integer id) {
        return mapper.selectById(id);
    }

    @Override
    public void editAdminBid(TAdmin admin) {
        try {
            mapper.updateById(admin);
        }catch (Exception e){
            e.printStackTrace();
            if (e instanceof DuplicateKeyException){
                throw  new UpdateAcctRepeatExcepyion(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
    }

    @Override
    public TAdmin getAdminByUserName(String rowUserName) {
        QueryWrapper<TAdmin> wrapper = new QueryWrapper<>();
        wrapper.eq(true,"login_acct",rowUserName);
        List<TAdmin> tAdmins = mapper.selectList(wrapper);
        TAdmin tAdmin = tAdmins.get(0);
        return tAdmin;
    }


}
