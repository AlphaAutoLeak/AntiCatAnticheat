package com.alphaautoleak.utils;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

import java.io.InputStream;
import java.lang.reflect.Method;

/**
 * @Author: SnowFlake
 * @Date: 2022/7/9 21:51
 */
public class ASMUtils {

    public static ClassNode readClass(Class<?> clazz){
        try {
            ClassNode classNode = new ClassNode();
            Method m = null;
            try {
                m = ClassReader.class.getDeclaredMethod("readClass", InputStream.class , boolean.class);
            }catch (NoSuchMethodException e)
            {
                m = ClassReader.class.getDeclaredMethod("a", InputStream.class , boolean.class);
            }
            m.setAccessible(true);
            byte[] bytes = (byte[]) m.invoke(null , Thread.currentThread().getContextClassLoader().getResourceAsStream(clazz.getName().replace('.', '/') + ".class"),true);
            ClassReader classReader = new ClassReader(bytes);
            classReader.accept(classNode,0);
            return classNode;
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }


    public static boolean isClass(byte[] bytes){
        if ((bytes[0] & 0xFF) == 0xCA && (bytes[1] & 0xFF) == 0xFE && (bytes[2] & 0xFF) == 0xBA && (bytes[3] & 0xFF) == 0xBE)
        {
            return true;
        }
        return false;
    }

}
