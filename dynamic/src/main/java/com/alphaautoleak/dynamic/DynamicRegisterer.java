package com.alphaautoleak.dynamic;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;

/**
 * @Author: SnowFlake
 * @Date: 2022/7/14 11:01
 */
public class DynamicRegisterer {

    public DynamicRegisterer(){
        registerNativeInvoke();
    }

    public static void registerNativeInvoke(){
        try {
            Class<?> clazz = Class.forName("moe.catserver.mc.cac.NativeLoader");
            String owner = clazz.getName().replace(".","./");
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods)
            {
                method.setAccessible(true);
                if (Modifier.toString(method.getModifiers()).contains("native"))
                {
                    Class<?> type = method.getReturnType();

                    if (type.equals(int.class))
                    {
                        if (method.getParameterCount() == 1)
                        {
                            //register the network method
                            registerFakeNative(owner,method.getName(),"(I)I");
                        }
                        else if (method.getParameterCount() == 0)
                        {
                            //register the init method
                            registerFakeNative(owner,method.getName(),"()I");
                        }
                    }else if (type.equals(void.class))
                    {
                        //old version register
                        registerFakeNative(owner,method.getName(),"()V");
                    }
                    else if (type.equals(boolean.class))
                    {
                        registerFakeNative(owner,method.getName(),"()Z");
                    }
                    else if (type.getName().contains("catserver"))
                    {
                        registerFakeNative(owner,method.getName(),"(Lmoe/catserver/mc/cac/NativeServerDynamicSandboxMessage;)Lmoe/catserver/mc/cac/NativeClientReportMessage;");

                    }
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static native void registerFakeNative(String owner,String name,String desc);

}
