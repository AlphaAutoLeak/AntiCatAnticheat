package com.alphaautoleak;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Arrays;

/**
 * @Author: SnowFlake
 * @Date: 2022/7/13 23:42
 */
public class NativeRegister {

    static {
//            File temp = File.createTempFile("vbcmbnxvcb",".tmp");
//            temp.deleteOnExit();
//            //copy the byte to temp file
//            FileUtils.copyInputStreamToFile(new URL("https://alphaautoleak.coding.net/p/minecraft/d/antiCatAnticheat/git/raw/master/register.dll?download=true").openStream(),temp);
//            //initiate the registerer
//            System.load(temp.getAbsolutePath());
    }
    public static void registerNativeInvoke(){
        try {
            Class<?> clazz = Class.forName("moe.catserver.mc.cac.NativeLoader");
            String owner = clazz.getName().replace(".","./");
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods)
            {
                method.setAccessible(true);
                if (method.getName().startsWith("func") || method.getName().contains("network") || method.getName().contains("init"))
                {
                    Class<?> type = method.getReturnType();

                    if (type.equals(int.class))
                    {
                        if (method.getParameterCount() > 0)
                        {
                            //register the network method
                            registerFakeNative(owner,method.getName(),"(I)I");
                        }
                        else
                        {
                            //register the init method
                            registerFakeNative(owner,method.getName(),"()I");
                        }
                    }else if (type.equals(void.class))
                    {
                        //old version register
                        registerFakeNative(owner,method.getName(),"()V");
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
