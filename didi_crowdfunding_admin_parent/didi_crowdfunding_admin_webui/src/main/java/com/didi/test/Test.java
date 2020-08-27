package com.didi.test;


import com.didi.entrty.admin.TAdmin;
import com.didi.entrty.role.TRole;
import com.didi.admin.mapper.TAdminMapper;
import com.didi.admin.service.impl.TAdminServiceImpl;
import com.didi.role.mapper.TRoleMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
@RunWith(SpringJUnit4ClassRunner.class)
 @ContextConfiguration(locations = {"classpath:admin/applicationContext.xml","classpath:admin/applicationContext-web.xml"})
public class Test {
    @Autowired
    private TAdminServiceImpl adminService;
   private Logger logger = LoggerFactory.getLogger(Test.class);
    @Autowired
    private TRoleMapper roleMapper;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private TAdminMapper mapper;

    @org.junit.Test
    public void testmapper(){
        List<TAdmin> tAdmins = mapper.selectList(null);
        logger.info(tAdmins.toString());
    }
    @org.junit.Test
    public void testSql() throws SQLException {
        logger.info("dataSource"+dataSource.toString());
        //获取数据库连接
        Connection connection = dataSource.getConnection();
        logger.info("connection"+connection.toString());
    }

    @org.junit.Test
    public  void testseve(){
        for (int i = 4;i<10;i++){
            TAdmin admin = new TAdmin(i,"abc"+i,"123","杰克","jieke@qq.com",null);
            mapper.insert(admin);
        }
    }
    @org.junit.Test
    public  void testlogger(){
    }

    @org.junit.Test
    public void severAdmin(){
        TAdmin tAdmin = adminService.querryAdminByLoginAct("aa", "asd");
    }
    @org.junit.Test
    public  void  testmapper2(){
        for (int i = 0;i<587;i++){
            TRole role = new TRole("role"+i);
            roleMapper.insert(role);
        }
    }
    @org.junit.Test
    public  void  testAssign(){
        List<TRole> assignedRole = roleMapper.getAssignedRole(1);
        logger.warn(String.valueOf(assignedRole));
    }
    @org.junit.Test
    public void testSpringXML(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/admin/applicationContext.xml");
        System.out.println(context);
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for (String beanName:beanDefinitionNames) {
            System.out.println("******************"+beanName);
        }
    }
    @org.junit.Test
    public void testAiliPay(){
        String aa = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgd+aftMFQ9RmZ818Ka+yzKAbFTv9SQOZm9EMg+LsTko6B3DqQViMGNa/ztthVU2WEIWdHF3jNsxkBXG7yN3TVpW54AkOI359MS/zPLjZ+t+KHxZlR685eCx2H4YAH6wOeWQtVAmyIhbnRyVnh8NScDpIp+eI3+zFS63sHMiyQHo4rlKzyVn6jfHIngzO8FUlEKCSvK1nC2Kf0lNDgT3X3AHENK+eIOFegcATCSG7rGjR3rD3LHBzXlsp9PuncGxUZPb8pJNCShu/Q57nvCWDOjk6x9ezqUm/x17B/6krd5iKLrt4QQti1y4NRf1WmLh30ltO23xk4oaB4EW39zJT7QIDAQAB";
        String bb = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgd+aftMFQ9RmZ818Ka+yzKAbFTv9SQOZm9EMg+LsTko6B3DqQViMGNa/ztthVU2WEIWdHF3jNsxkBXG7yN3TVpW54AkOI359MS/zPLjZ+t+KHxZlR685eCx2H4YAH6wOeWQtVAmyIhbnRyVnh8NScDpIp+eI3+zFS63sHMiyQHo4rlKzyVn6jfHIngzO8FUlEKCSvK1nC2Kf0lNDgT3X3AHENK+eIOFegcATCSG7rGjR3rD3LHBzXlsp9PuncGxUZPb8pJNCShu/Q57nvCWDOjk6x9ezqUm/x17B/6krd5iKLrt4QQti1y4NRf1WmLh30ltO23xk4oaB4EW39zJT7QIDAQAB";
        String dd = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApVvIRieVviHmU4W5bJ0On+t1E5NU2CKSx6/T0l/tMQyEkjDjnE3FlSenb2baGdLg49/WyXITSTBL+QhmemMmKj26eLFbpb+2STRlN4oLAGdKKAZTp4L4PJ+LhR7ob5FHgEAPRepwllvTRAbw9AMQnGtPO2dTd+FlkyDRt0J3kOgO2LJS/XqL+tWTxsrVq57a1aKv/J/BVbrPrQVu9AaSTjcBEBXHbLBzCf8exLUdeSk2Cl+DAp9whlwgaWS8RUKrtIz3amwtXYT7O5i/rsXbx6wfCw4rKgW1Mr81Isd6Q5Q2it+ondYHf15kDuzr9+2wqbF7suCdg8SaXa+p9zVnCwIDAQAB";
        String ee= "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApVvIRieVviHmU4W5bJ0On+t1E5NU2CKSx6/T0l/tMQyEkjDjnE3FlSenb2baGdLg49/WyXITSTBL+QhmemMmKj26eLFbpb+2STRlN4oLAGdKKAZTp4L4PJ+LhR7ob5FHgEAPRepwllvTRAbw9AMQnGtPO2dTd+FlkyDRt0J3kOgO2LJS/XqL+tWTxsrVq57a1aKv/J/BVbrPrQVu9AaSTjcBEBXHbLBzCf8exLUdeSk2Cl+DAp9whlwgaWS8RUKrtIz3amwtXYT7O5i/rsXbx6wfCw4rKgW1Mr81Isd6Q5Q2it+ondYHf15kDuzr9+2wqbF7suCdg8SaXa+p9zVnCwIDAQAB";
        boolean cc = dd.equals(ee);
        System.out.println(cc);
    }

}
