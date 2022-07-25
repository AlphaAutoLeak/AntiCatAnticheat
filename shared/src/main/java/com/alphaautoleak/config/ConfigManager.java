package com.alphaautoleak.config;

import com.alphaautoleak.utils.Utils;
import com.google.gson.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

/**
 * @Author: SnowFlake
 * @Date: 2022/7/24 20:21
 */
public class ConfigManager
{
    public static String fakehwid = Utils.getHWID().substring(2,6);
    public static File file = new File("D:/acac"+fakehwid+"/");
    public static File image = new File(file.getAbsoluteFile()+"/image"+fakehwid+"/");
    public static File configPath = new File(file.getAbsoluteFile()+"/config"+fakehwid+".json");
    public static Config config = new Config();

    public ConfigManager()
    {
        Random random = new Random();
        try
        {
            if (!file.exists())
            {
                file.mkdir();
            }

            if (!image.exists())
            {
                image.mkdir();
            }

            if (!configPath.exists())
            {

                GsonBuilder gsonBuilder = new GsonBuilder();

                gsonBuilder.excludeFieldsWithoutExposeAnnotation();

                Gson gson = gsonBuilder.setPrettyPrinting().create();

                config.setAutoMode(false);

                config.setCancelleScreenShot(false);

                config.setScreenShotText("QQ951397728");

                ArrayList<String> exclude = new ArrayList<>();
                exclude.add("chentg");
                exclude.add("alphaautoleak");
                exclude.add("AZRAELPROTECTFANCHENPROTECT");
                config.setExcludeList(exclude);

                ArrayList<Long> qqList = new ArrayList<>();
                qqList.add(Utils.getLongRandom(10));
                qqList.add(Utils.getLongRandom(10));
                qqList.add(Utils.getLongRandom(10));
                config.setQqList(qqList);

                config.setMac((new byte[]{(byte) random.nextInt(80), (byte) random.nextInt(80), (byte) random.nextInt(80), (byte) random.nextInt(80), (byte) random.nextInt(80), (byte) random.nextInt(80)}));

                config.setMacInfo("Realtek PCIe GBE Family Controller");

                String out = gson.toJson(config);

                Utils.write(out,configPath.getAbsolutePath());

            }
            else
            {
                this.updateData();
            }

        }
        catch (Exception e)
        {

            e.printStackTrace();
        }
    }

    public void updateData()
    {

        try {
            String input = Utils.read(configPath.getAbsolutePath()); // get the config content

            Gson gson = new Gson();

            JsonObject jsonObject = (JsonObject) new JsonParser().parse(input).getAsJsonObject();

            ConfigManager.config.setAutoMode(jsonObject.get("autoMode").getAsBoolean());


            ArrayList<Long> qqList = new ArrayList<>();
            for (JsonElement array : jsonObject.get("qqList").getAsJsonArray())
            {
                qqList.add(array.getAsLong());
            }
            ConfigManager.config.setQqList(qqList);

            ArrayList<String> excludeList = new ArrayList<>();
            for (JsonElement array : jsonObject.get("excludeList").getAsJsonArray())
            {
                excludeList.add(array.getAsString());
            }

            ConfigManager.config.setExcludeList(excludeList);

            ConfigManager.config.setCancelleScreenShot(jsonObject.get("cancelleScreenShot").getAsBoolean());

            ConfigManager.config.setEnableCustomScreenShotText(jsonObject.get("enableCustomScreenShotText").getAsBoolean());

            ConfigManager.config.setScreenShotText(jsonObject.get("screenShotText").getAsString());

            ConfigManager.config.setMacInfo(jsonObject.get("macInfo").getAsString());

            byte[] macList = new byte[6];
            int i = 0;

            for (JsonElement element : jsonObject.get("macList").getAsJsonArray()) {
                macList[i] = element.getAsByte();
                i++;
            }
            ConfigManager.config.setMac(macList);


            Utils.debug(""+config.toString());

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }




}
