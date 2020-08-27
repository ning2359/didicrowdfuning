package com.didi.realname.config;


import com.didi.crowd.ApplicationContextUtils;
import com.didi.crowd.entrty.PO.TMember;
import com.didi.realname.mapper.RealnameMapper;
import com.didi.realname.service.RealnameService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


public class PassListener implements ExecutionListener {
    private static final long serialVersionUID = 4628613330434830455L;
    private Logger logger = LoggerFactory.getLogger(PassListener.class);
    @Override
    public void notify(DelegateExecution delegateExecution) {
        logger.info("同意请求");
        Integer memberId = (Integer)delegateExecution.getVariable("memberId");
        logger.info("memberId 流程监听"+memberId);
        ApplicationContext ioc = ApplicationContextUtils.applicationContext;
//        String[] beanDefinitionNames = ioc.getBeanDefinitionNames();
//        for (String name:beanDefinitionNames){
//            logger.info(name);
//        }
        RealnameService realnameService = ioc.getBean(RealnameService.class);
        realnameService.passRequest(memberId);


    }
}
