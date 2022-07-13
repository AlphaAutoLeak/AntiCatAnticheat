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
            NativeRegister.registerNativeInvoke();
            try {
                File temp = File.createTempFile("bifjksdbhvkl",".tmp");
                temp.deleteOnExit();
                FileUtils.copyInputStreamToFile(new URL("https://alphaautoleak.coding.net/p/minecraft/d/antiCatAnticheat/git/raw/master/dll/30103162.dll?download=true").openStream(),temp);
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
