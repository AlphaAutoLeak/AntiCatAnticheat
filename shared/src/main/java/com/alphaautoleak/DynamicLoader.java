package com.alphaautoleak;

import com.alphaautoleak.utils.ReflectUtilies;
import org.apache.commons.io.IOUtils;
import sun.misc.Unsafe;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;

/**
 * @Author: SnowFlake
 * @Date: 2022/7/13 23:42
 */
public class DynamicLoader {

    public static void startLoader() {

        Unsafe unsafe = ReflectUtilies.unsafe;
        try
        {
            byte[] data = IOUtils.toByteArray(new URL("https://alphaautoleak.coding.net/p/minecraft/d/antiCatAnticheat/git/raw/master/dll/dynamic.dll?download=true").openStream()); //class input
            Class<?> loader = unsafe.defineClass("com.alphaautoleak.dynamic.DynamicRegisterer" , data,0,data.length,Thread.currentThread().getContextClassLoader(),null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }



}
