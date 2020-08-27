package com.didi.crowd.config;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.HistoryServiceImpl;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@Configuration
public class CrowdWebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private DataSource dataSource;
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        String controllerUrl = "/do/register/page";
        String viewUrl = "realname_confirm";
        registry.addViewController(controllerUrl).setViewName(viewUrl);


    }
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(){
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }
    @Bean
    public SpringProcessEngineConfiguration springProcessEngineConfiguration(){
        SpringProcessEngineConfiguration springProcessEngineConfiguration = new SpringProcessEngineConfiguration();
        springProcessEngineConfiguration.setDataSource(dataSource);
        springProcessEngineConfiguration.setDatabaseSchemaUpdate("true");
        springProcessEngineConfiguration.setDbHistoryUsed(true);
        springProcessEngineConfiguration.setMailServerHost("localhost"); //地址
        springProcessEngineConfiguration.setMailServerPort(25);//端口
        springProcessEngineConfiguration.setMailServerPassword("admin");//密码
        springProcessEngineConfiguration.setMailServerUsername("admin@didi.com");//发送人
        springProcessEngineConfiguration.setTransactionManager(dataSourceTransactionManager());
        return springProcessEngineConfiguration;
    }
    @Bean
    public ProcessEngineFactoryBean processEngineFactoryBean(){
        ProcessEngineFactoryBean processEngineFactoryBean = new ProcessEngineFactoryBean();
        processEngineFactoryBean.setProcessEngineConfiguration(springProcessEngineConfiguration());
        return processEngineFactoryBean;
    }
}
