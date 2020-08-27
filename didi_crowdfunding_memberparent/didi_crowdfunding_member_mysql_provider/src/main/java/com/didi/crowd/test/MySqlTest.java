package com.didi.crowd.test;

import com.didi.crowd.entrty.PO.TMember;
import com.didi.crowd.entrty.VO.DetailProjectVO;
import com.didi.crowd.mapper.TMemberMapper;
import com.didi.crowd.mapper.TProjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MySqlTest {
    private Logger logger = LoggerFactory.getLogger(MySqlTest.class);
    @Autowired
    private DataSource dataSource;
    @Autowired
    private TMemberMapper memberMapper;
    @Autowired
    private TProjectMapper projectMapper;
    @Test
    public void testConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        logger.debug(String.valueOf(connection));
    }
    @Test
    public void testInsert(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("123123");

//        TMember member = new TMember("aa",encode,"杰克","1062664904@qq.cpm",1,1,"dd","aa",1);
//        memberMapper.insert(member);
    }
    @Test
    public void testProjectCount(){
        Integer projectId = 40;
        DetailProjectVO detailProjectVO = projectMapper.selectDetailProjectVO(projectId);
        logger.info("detailProjectVO"+detailProjectVO.toString());
    }
}
