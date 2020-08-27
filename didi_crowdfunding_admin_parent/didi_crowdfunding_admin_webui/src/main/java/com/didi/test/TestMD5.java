package com.didi.test;

import com.didi.encrypd.MD5Encrypted;
import org.junit.Test;

public class TestMD5 {
    @Test
    public  void  testmd5(){
        String source = "123123";
        String s = MD5Encrypted.md5(source);
        System.out.println(s);
    }
}
