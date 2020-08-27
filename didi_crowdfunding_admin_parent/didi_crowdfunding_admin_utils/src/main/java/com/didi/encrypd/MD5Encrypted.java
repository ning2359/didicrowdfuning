package com.didi.encrypd;

import com.didi.crowd.CrowdConstant;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Encrypted {
    public static String md5(String source){
        if (source==null || source.length()==0){
            throw  new RuntimeException(CrowdConstant.MESSAGE_NAME_EXCEPTION);
        }
        String algorithm = "md5";
        try {
            MessageDigest instance = MessageDigest.getInstance(algorithm);
            byte[] input = source.getBytes();
            byte[] output = instance.digest(input);
            int signum = 1;
            BigInteger bigInteger = new BigInteger(signum,output);
            int redrix = 16;
            String encoded = bigInteger.toString(redrix).toUpperCase();
            return encoded;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
