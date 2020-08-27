package com.didi.crowd.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CrowdTest {

    private Logger logger = LoggerFactory.getLogger(CrowdTest.class);

    @Test
    public void testSendMessage() {

    }
    @Test
    public  void testPhone(){
        String phoneNum = "";
        if (!(phoneNum != null && phoneNum.length()>0 && phoneNum.length()<=11)){
        }

    }
    @Test
    public void testAliyunSMS(){

    }

}
