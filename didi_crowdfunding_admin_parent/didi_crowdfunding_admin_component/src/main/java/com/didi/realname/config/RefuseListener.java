package com.didi.realname.config;


import com.didi.crowd.ApplicationContextUtils;
import com.didi.realname.service.RealnameService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;


public class RefuseListener implements ExecutionListener {

    private Logger logger = LoggerFactory.getLogger(RefuseListener.class);
    private static final long serialVersionUID = -8458863734903747679L;

    @Override
    public void notify(DelegateExecution delegateExecution) {
        logger.info("拒绝请求");
        Integer memberId = (Integer)delegateExecution.getVariable("memberId");
        logger.info("memberId 流程监听"+memberId);
        ApplicationContext ioc = ApplicationContextUtils.applicationContext;
//        String[] beanDefinitionNames = ioc.getBeanDefinitionNames();
//        for (String name:beanDefinitionNames){
//            logger.info(name);
//        }
        RealnameService realnameService = ioc.getBean(RealnameService.class);
        realnameService.rejectRequest(memberId);

    }
}
