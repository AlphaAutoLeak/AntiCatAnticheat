package com.alphaautoleak;

import com.alphaautoleak.antianticheats.AntiAntiCheatManager;
import com.alphaautoleak.thread.AntiDebugThread;
import com.alphaautoleak.utils.ReflectUtilies;
import com.alphaautoleak.utils.Utils;

import java.io.File;
import java.io.IOException;

/**
 * @Author: SnowFlake
 * @Date: 2022/7/9 21:45
 */
public class AntiCatAntiCheat {
    public static boolean oldVersion = true;
    public AntiAntiCheatManager antiAntiCheatManager;

    public static String fakehwid;

    static {
        fakehwid = Utils.getHWID().substring(2,6);
    }
    public static File file = new File("D:/acac"+fakehwid+"/");
    public static File image = new File(file.getAbsoluteFile()+"/image"+fakehwid+"/");
    public static File exclude = new File(file.getAbsoluteFile()+"/classExclude"+fakehwid+".txt");
    public static File text = new File(file.getAbsoluteFile()+"/screenshot"+fakehwid+".txt");


    public static String contents = null;

    public AntiCatAntiCheat()
    {
        new AntiDebugThread().start();

        if (!file.exists())
        {
            file.mkdir();
        }

        if (!image.exists())
        {
            image.mkdir();
        }


        try {

            if (!text.exists())
            {
                text.createNewFile();
                Utils.write("#customText是否启用自定义截图水印( false为 否 true为 是)\n" +
                                    "#text水印内容\n" +
                                    "customText>false\n" +
                                    "text>反猫反超级群聊951397728\n"
                        ,text.getAbsolutePath());
            }

            if (!exclude.exists())
            {
                exclude.createNewFile();
                Utils.write("#格式(顶格)-加你的类或包\n#将检测类的名字中取关键词,例如禁用类名字叫luohuayu.anticheat.plugin.PlayerHandler,我们可以取其中的luohuayu或者anticheat\n#如何知道这些字符?\n#右键通过压缩包方式打开,就可以看见很多文件夹,抄写下来输入到下面即可xxxxx\n#你也可以根据下面预设的来写\n#例子1: -luohuayu.anticheat\n#例子2: -luohuayu.anticheat.plugin.CloudData\n#例子2比例子1更加精准\n-chentg\n-alphaautoleak\n-AZRAELPROTECTFANCHENPROTECT\n",exclude.getAbsolutePath());
            }

        }catch (Exception e)
        {


        }
        contents = Utils.doGet("http://cn.aurorateam.online/cathwid.php");
        antiAntiCheatManager = new AntiAntiCheatManager();
    }

    public static void exitServer(){

    }

}
