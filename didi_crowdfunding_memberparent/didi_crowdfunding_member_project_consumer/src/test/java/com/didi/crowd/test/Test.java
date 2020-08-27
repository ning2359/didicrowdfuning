package com.didi.crowd.test;

import com.didi.crowd.CrowdUtil;
import com.didi.crowd.ResultEntity;
import com.didi.crowd.config.OSSProperties;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


@RunWith(SpringRunner.class)
@SpringBootTest
public class Test {
    @Autowired
    private OSSProperties properties;
    @org.junit.Test
    public void testUpload() throws FileNotFoundException {
        FileInputStream inputStream = new FileInputStream("644756c04f0c.jpg");
        String fileName = "644756c04f0c.jpg";
        ResultEntity<String> stringResultEntity = CrowdUtil.uploadFileToOss(
                properties.getEndPoint(),
                properties.getAccessKeyId(),
                properties.getAccessKeySecret(),
                inputStream,
                properties.getBucketName(),
                properties.getBucketDomain(),
                fileName
        );
        System.out.println(stringResultEntity);
    }
}
