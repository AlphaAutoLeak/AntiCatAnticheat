package com.alphaautoleak;

import com.alphaautoleak.antianticheats.AntiAntiCheatManager;
import com.alphaautoleak.config.ConfigManager;
import com.alphaautoleak.thread.AntiDebugThread;
import com.alphaautoleak.utils.ReflectUtilies;
import com.alphaautoleak.utils.Utils;
import com.alphaautoleak.utils.Version;

import java.io.File;
import java.io.IOException;

/**
 * @Author: SnowFlake
 * @Date: 2022/7/9 21:45
 */
public class AntiCatAntiCheat {
    public static boolean oldVersion = true;
    public AntiAntiCheatManager antiAntiCheatManager;

    public static AntiCatAntiCheat instance;
    public ConfigManager configManager;
    public static String contents = null;

    public AntiCatAntiCheat(Version version)
    {
        instance = this;
        new AntiDebugThread().start();

        try {
            contents = Utils.doGet("http://cn.aurorateam.online/cathwid.php");

            if (contents.contains(Utils.md5(Utils.getHWID())))
            {
                configManager = new ConfigManager();
                antiAntiCheatManager = new AntiAntiCheatManager();
            }else{
                while (true)
                {
                    System.out.println("验证未通过");
                }
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }


    }

}
