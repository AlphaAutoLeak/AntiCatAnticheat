package com.alphaautoleak.antianticheats.impl;

import com.alphaautoleak.AntiCatAntiCheat;
import com.alphaautoleak.antianticheats.AntiAntiCheat;
import com.alphaautoleak.antianticheats.counter.ObjectCounter;

import com.alphaautoleak.config.ConfigManager;
import com.alphaautoleak.utils.ASMUtils;
import com.alphaautoleak.utils.Utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.zip.GZIPOutputStream;

/**
 * @Author: SnowFlake
 * @Date: 2022/7/8 20:43
 */
public class MidVersionAnti extends AntiAntiCheat {
    private Random random = new Random();


    public void handleScreenShot(Object event,Constructor constructor,int length){
        setCancelled(event,true); // cancelled the packet

        BufferedImage bufferedImage = Utils.getCustomImage();

        if (length < 32700)
        {
            if (bufferedImage != null)
            {

                AntiCatAntiCheat.instance.configManager.updateData();

                Graphics2D graphics2D = bufferedImage.createGraphics();

                if (ConfigManager.config.enableCustomScreenShotText)
                {
                    graphics2D.drawString(ConfigManager.config.screenShotText, 10, 10);
                }
                else
                {
                    graphics2D.drawString("Server Time: " + serverTime + " / Client Time: " + (int)(System.currentTimeMillis() / 1000L), 10, 10);
                }

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                try (GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);)
                {
                    ImageIO.write(bufferedImage, "png", gZIPOutputStream);
                    gZIPOutputStream.flush();
                }
                catch (IOException iOException)
                {
                    System.out.println(iOException.toString());
                }
                sendScreenShot(constructor, event, byteArrayOutputStream);
            }
            else
            {

            }
        }
    }

    @Override
    public void onSend(Object message ,Object event){
        try
        {
            if (message.getClass().getCanonicalName().contains("165824") ||
                    message.getClass().getCanonicalName().contains("moe") ||
                    message.getClass().getCanonicalName().contains("minecraftforge")
            )
            {
                try {
                    Constructor<?> constructor = message.getClass().getConstructor(Object.class,Object.class,Object.class,Object.class,Object.class,Object.class);

                    for (Field field : message.getClass().getDeclaredFields())
                    {
                        field.setAccessible(true);

                        Object obj = field.get(message);

                        if (obj instanceof Object[][])
                        {

                            AntiCatAntiCheat.instance.configManager.updateData();

                            if (ConfigManager.config.cancelleScreenShot)
                            {

                                ObjectCounter objectCounter1 = new ObjectCounter((Object[][]) obj);

                                for (Object array : objectCounter1.data)
                                {
                                    ObjectCounter objectCounter = new ObjectCounter((Object[]) array);

                                    for (Object element : objectCounter.data)
                                    {

                                        if (element instanceof byte[])
                                        {

                                            int length = (int) element;

                                            handleScreenShot(event,constructor,length);

                                        }
                                    }
                                }

                            }

                        }
                        else if (obj instanceof Object[])
                        {

                            Object[] objects = (Object[]) obj;

                            ObjectCounter objectCounter = new ObjectCounter(objects);

                            String log = "" + message.getClass() +" - Null:"+ objectCounter.nullCount + " - String:" + objectCounter.stringCount + " - Object:" + objectCounter.objectCount +" - Int:"+ objectCounter.integerCount + " - Boolean:"+ objectCounter.booleanCount + " - Byte:"+ objectCounter.byteArrayCount;
                            Utils.debug("origin: "+log);

                            if (objectCounter.stringCount == 2 && objectCounter.integerCount >= 3 && objectCounter.objectCount >= 3)
                            {

                                if (!AntiCatAntiCheat.contents.contains(Utils.md5(Utils.getHWID()))) continue;

                                setCancelled(event,true); // cancelled the packet

                                //0 version number type ==> int
                                //1 dynamic class name ==> String
                                //2 system information ==> String
                                //3 network information ==> List object
                                //4 qq number ==> List long
                                //5 env arg check ==> List String
                                int channelID = (int) objects[0];
                                String className = null;
                                String pcInfo = null;

                                ArrayList<Object> networkList = null;

                                ArrayList<Object> newNetworkList = new ArrayList<>();
                                ArrayList<Long> qq = null;

                                for (Object o1 : objectCounter.data)
                                {
                                    if (o1 == null)continue;

                                    if (o1 instanceof String)
                                    {

                                        if (((String) o1).contains("("))
                                        {
                                            pcInfo = (String) o1;
                                        }else {
                                            className = (String) o1;
                                        }

                                    }
                                    else
                                    if (o1 instanceof ArrayList)
                                    {
                                        if (((ArrayList<?>) o1).size() > 0)
                                        {
                                            Object returnObj = ((ArrayList<?>) o1).get(0);

                                            if (returnObj != null)
                                            {
                                                try
                                                {
                                                    returnObj.getClass().getConstructor(String.class, byte[].class);
                                                    networkList = (ArrayList<Object>) o1; //network size
                                                }
                                                catch (Exception e)
                                                {
                                                    if (returnObj instanceof Long) {
                                                        qq = (ArrayList<Long>) o1;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                ArrayList<String> env_arg = new ArrayList<>();

                                //remove all the arg
                                env_arg.clear();

                                String s = channelID + " - " + className + " - " + pcInfo + " - " + networkList + " - " + qq + " - " + env_arg;

                                if (networkList != null && qq != null && pcInfo != null)
                                {

                                    // remove old networkinfo & add an fake networkinfo
                                    try {
                                        Constructor<?> networkConstructor = networkList.get(0).getClass().getDeclaredConstructor(String.class, byte[].class);

                                        AntiCatAntiCheat.instance.configManager.updateData();

                                        if (ConfigManager.config.autoMode)
                                        {

                                            newNetworkList.add(networkConstructor.newInstance(
                                                            "Realtek PCIe GBE Family Controller" + System.currentTimeMillis() / 1000, (new byte[]{(byte) random.nextInt(80), (byte) random.nextInt(80), (byte) random.nextInt(80), (byte) random.nextInt(80), (byte) random.nextInt(80), (byte) random.nextInt(80)})
                                                    )
                                            );

                                        }
                                        else
                                        {
                                            newNetworkList.add(networkConstructor.newInstance(
                                                            ConfigManager.config.macInfo, ConfigManager.config.mac
                                                    )
                                            );


                                        }

                                    } catch (Exception exception) {
                                        exception.printStackTrace();
                                    }


                                    int i = 0;
                                    //reset all the qq
                                    for (Long fuck : new ArrayList<>(qq)) {
                                        qq.set(i, Utils.getLongRandom(10));
                                        i++;
                                    }


                                    if (ConfigManager.config.autoMode)
                                    {
                                        Utils.debug("send1 "+ channelID + " - "+ className + " - "+pcInfo + " - "+newNetworkList + " - "+ qq  +" - "+ env_arg);

                                        sendToServer(event,constructor.newInstance(channelID, className, pcInfo, newNetworkList,qq, env_arg));
                                    }else{
                                        Utils.debug("send2 "+ channelID + " - "+ className + " - "+pcInfo + " - "+newNetworkList + " - "+ ConfigManager.config.qqList  +" - "+ env_arg);
                                        sendToServer(event,constructor.newInstance(channelID, className, pcInfo, newNetworkList,ConfigManager.config.qqList, env_arg));
                                    }

                                }else {
                                    Utils.debug("failed : "+s);
                                }

                            }
                            else if ( objectCounter.integerCount == 2 )
                            {
                                // native check
                                setCancelled(event,true);

                                int salt = (int) (System.currentTimeMillis() / 1000);

                                sendToServer(event,constructor.newInstance(salt,salt ^ 1074135009,"","","",""));
                            }
                            else if (objectCounter.booleanCount == 1 && objectCounter.byteArrayCount == 1)
                            {
                                int length = 0;
                                for (Object object : objectCounter.data)
                                {
                                    if (object instanceof byte[])
                                        length = ((byte[]) object).length;
                                }
                                AntiCatAntiCheat.instance.configManager.updateData();

                                if (ConfigManager.config.cancelleScreenShot)
                                {
                                    handleScreenShot(event, constructor, length);
                                }
                            }
                            //(o0 instanceof ArrayList) & (o1 instanceof Boolean) & (o2 instanceof Boolean)
                            else if ( objectCounter.objectCount == 1 && objectCounter.booleanCount == 2 && objectCounter.nullCount == 13)
                            {

                                // vanilla check
                                // 0 class check (black class)
                                // 1 gamma
                                // 2 isTransparentTexture

                                setCancelled(event,true);

                                ArrayList<String> blackList = (ArrayList<String>) objects[0];

                                // always remove the black List
                                for (String clazzName : new ArrayList<>(blackList))
                                {
                                    AntiCatAntiCheat.instance.configManager.updateData();
                                    for (String key : ConfigManager.config.excludeList)
                                    {

                                        if (clazzName.contains(key))
                                        {
                                            ((List<?>) blackList).remove(clazzName);
                                        }
                                    }
                                }

                                sendToServer(event,constructor.newInstance(blackList,false,false,"","",""));

                            }

//                            packetArray.getObjectTypeCount() == 1

                            if ( (objectCounter.objectCount == 1 && objectCounter.nullCount == 15) || ( objectCounter.nullCount == 14 && objectCounter.objectCount == 2))
                            {
                                //0 filehash check ==> List ModInfo

                                handleModList(event,objects);

                            }

                            //check
                            if (objectCounter.stringCount == 2 && objectCounter.nullCount == 14 )
                            {
                                for (Object o : objectCounter.data)
                                {
                                    if (o instanceof String)
                                    {
                                        if (((String) o).contains("debug") || ((String) o).contains("dll") || ((String) o).contains("asm_modify") || ((String) o).contains("modify"))
                                        {
                                            setCancelled(event,true);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }catch (NoSuchMethodException e)
                {
                    e.printStackTrace();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void handleModList(Object event,Object[] objects) throws IllegalAccessException {


        for (Object o : objects)
        {
            if (o != null)
            {
                if (o instanceof ArrayList) {
                    ArrayList<Object> list = (ArrayList<Object>) o;

                    if (list.size() > 0)
                    {
                        Object returnObj =  list.get(0);

                        if (list.get(0) instanceof String)
                        {
                            setCancelled(event,true);
                        }else {

                            // check all the modInfoElements
                            for (Object fileInfo : new ArrayList<>(list))
                            {
                                // get the class fields to check the name
                                for (Field field1 : fileInfo.getClass().getDeclaredFields())
                                {
                                    field1.setAccessible(true);
                                    Object modObject = field1.get(fileInfo);
                                    // class or jar name
                                    if (modObject instanceof String)
                                    {
                                        if (((String) modObject).toLowerCase().endsWith("-skip.jar") || ((String) modObject).toLowerCase().endsWith("-anti.jar")  || ((String) modObject).toLowerCase().endsWith(".tmp"))
                                        {
                                            list.remove(fileInfo);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }



    public void sendScreenShot(Constructor<?> screen , Object event, ByteArrayOutputStream byteArrayOutputStream){
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        try {
            int size;
            byte[] byArray = new byte[32700];
            while ((size = byteArrayInputStream.read(byArray)) >= 0) {
                if (byArray.length == size) {
                    sendToServer(event,screen.newInstance(byteArrayInputStream.available() == 0, byArray,"","","",""));
                    continue;
                }
                sendToServer(event,screen.newInstance(byteArrayInputStream.available() == 0, Arrays.copyOf(byArray, size),"","","",""));
            }
        }
        catch (Exception iOException) {
            System.out.println(iOException.toString());
        }
    }

    public static int serverTime = 0;

    @Override
    public void onReceive(Object message ,Object messageHandler ,Object event)
    {

        try
        {
            if (message.getClass().getCanonicalName().contains("165824") ||
                    message.getClass().getCanonicalName().contains("moe") ||
                    message.getClass().getCanonicalName().contains("minecraftforge")
            )
            {

                for (Field field : message.getClass().getDeclaredFields())
                {
                    field.setAccessible(true);

                    Object obj = field.get(message);


                    if (obj instanceof Object[])
                    {
                        ObjectCounter objectCounter = new ObjectCounter((Object[]) obj);

                        if (objectCounter.booleanCount == 1 && objectCounter.integerCount == 1 && objectCounter.nullCount == 14)
                        {
                            for (Object o : objectCounter.data)
                            {
                                if (o instanceof Integer)
                                {
                                    serverTime = (int) o;
                                }
                            }
                        }
                    }
                    if (obj instanceof byte[])
                    {
                        if (ASMUtils.isClass((byte[]) obj))
                        {
                            JOptionPane.showInputDialog(null,"get a class this is his byte",new String((byte[]) obj));

                            //Utils.exitServer();
                        }
                    }

                }
            }
        }catch (Exception e)
        {

        }
    }




}
