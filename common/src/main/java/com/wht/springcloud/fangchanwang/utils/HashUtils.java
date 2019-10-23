package com.wht.springcloud.fangchanwang.utils;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;

/**
 * 对密码加盐
 */
public class HashUtils {

    //Guava的工具类,获取加密函数
    private static final HashFunction FUNCTION = Hashing.sha256();
    //盐，自定义字符串
    private static final String SALT="com.wht";

    /**
     * 密码加密
     * @param pwd
     * @return
     */
    public static String encryPwd(String pwd){
        HashCode hashCode = FUNCTION.hashString(pwd+SALT, Charset.forName("UTF-8"));
        return  hashCode.toString();
    }
}
