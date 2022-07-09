package com.alphaautoleak.utils;

/**
 * @Author: SnowFlake
 * @Date: 2022/7/9 21:51
 */
public class ASMUtils {

    public static boolean isClass(byte[] bytes){
        if ((bytes[0] & 0xFF) == 0xCA && (bytes[1] & 0xFF) == 0xFE && (bytes[2] & 0xFF) == 0xBA && (bytes[3] & 0xFF) == 0xBE)
        {
            return true;
        }
        return false;
    }

}
