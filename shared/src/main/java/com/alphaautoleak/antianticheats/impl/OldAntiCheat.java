package com.alphaautoleak.antianticheats.impl;

import com.alphaautoleak.AntiCatAntiCheat;
import com.alphaautoleak.antianticheats.AntiAntiCheat;

import com.alphaautoleak.utils.ASMUtils;
import com.alphaautoleak.utils.Utils;
import com.darkmagician6.eventapi.EventTarget;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;
import java.util.zip.GZIPOutputStream;

/**
 * @Author: SnowFlake
 * @Date: 2022/7/8 20:34
 */
public class OldAntiCheat extends AntiAntiCheat {
    public Random random = new Random();


    public void onReceive(Object message ,Object event){

        try {
            if (message.getClass().getCanonicalName().contains("165824") ||
                    message.getClass().getCanonicalName().contains("moe") ||
                    message.getClass().getCanonicalName().contains("minecraftforge")
            )
            {
                for (Field field : message.getClass().getDeclaredFields())
                {
                    field.setAccessible(true);

                    Object obj = field.get(message);

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

    public void onSend(Object message ,Object event){

        if (message.getClass().getCanonicalName().contains("165824") ||
                message.getClass().getCanonicalName().contains("moe") ||
                message.getClass().getCanonicalName().contains("minecraftforge")
        )
        {
            try {
                //the vanilla packet

                // 0.channel ID
                // 1.reflection name
                // 2.System info (like : win 10)
                // 3.network information (hardware id)
                // 4.All QQ in your computer
                // 5.your vm InputArguments
                Constructor<?> vanilla = message.getClass().getDeclaredConstructor(int.class,String.class,String.class, List.class,List.class,List.class);

                if (vanilla != null)
                {
                    for (Field field : message.getClass().getDeclaredFields())
                    {
                        field.setAccessible(true);

                        Object obj = field.get(message);

                        if (obj instanceof List)
                        {

                            Object o = ((List<?>) obj).get(0);

                            if ( !((List<?>) obj).isEmpty() && obj != null)
                            {
                                if (o instanceof Long)
                                {
                                    //qq List
                                    field.set(message, Arrays.asList(Utils.getLongRandom(10), Utils.getLongRandom(10), Utils.getLongRandom(10), Utils.getLongRandom(10)));

                                }else if (o instanceof String)
                                {
                                    //vm InputArguments
                                    field.set(message, Collections.singletonList(""));
                                }
                                else
                                {
                                    // find NetWorkData
                                    // mac
                                    try {
                                        Constructor<?> constructor = o.getClass().getDeclaredConstructor(String.class,byte[].class);

                                        if (constructor != null)
                                        {

                                            List<Object> networkdata = (List<Object>) field.get(message);

                                            networkdata.clear();

                                            networkdata.add(
                                                    constructor.newInstance(
                                                            "Realtek PCIe GBE Family" + System.currentTimeMillis() / 1000, (new byte[]{(byte)random.nextInt(80),(byte)random.nextInt(80),(byte)random.nextInt(80),(byte)random.nextInt(80),(byte)random.nextInt(80),(byte)random.nextInt(80)})
                                                    )
                                            );

                                        }
                                    }catch (Exception e2)
                                    {
                                        System.err.println("error"+e2.getMessage());
                                    }
                                }
                            }
                        }else if (obj instanceof Integer) {
                            // do nothings
                        }else if (obj instanceof String) {
                            //do nothings

                        }
                        //eventFMLChannels.setCancelled(true);
                    }
                }
            }catch (Exception e1)
            {

                try {

                    Constructor<?> constructor = message.getClass().getDeclaredConstructor(List.class);

                    if (constructor != null)
                    {

                        for (Field field : message.getClass().getDeclaredFields())
                        {
                            field.setAccessible(true);

                            Object obj = field.get(message);

                            if (obj != null)
                            {
                                if (obj instanceof List)
                                {
                                    Object o = ((List<?>) obj).get(0);

                                    if (o instanceof String)
                                    {
                                        //CInjectionMessage
                                        setCancelled(event,true);
                                        System.out.println(obj);
                                    }
                                    else
                                    {
                                        //CPacketFileHash
                                        //fields
                                        // 0. ModList Data
                                        // 1. radon boolean
                                        try
                                        {
                                            for (Object o1 : new ArrayList<>((List)obj))
                                            {

                                                for (Field fInfo : o1.getClass().getDeclaredFields())
                                                {
                                                    fInfo.setAccessible(true);

                                                    Object data = fInfo.get(o1);

                                                    if (data instanceof String)
                                                    {

                                                        String fileName = (String) fInfo.get(o1);

                                                        if (fileName.toLowerCase().endsWith("-anti.jar") || fileName.toLowerCase(Locale.ROOT).endsWith(".tmp"))
                                                        {
                                                            //remove your mod in list
                                                            ((List<?>) obj).remove(o1);
                                                        }
                                                    }
                                                }

                                            }
                                        }
                                        catch (Exception e)
                                        {
                                            System.out.println(e.getMessage());
                                        }
                                    }
                                }
                            }
                        }
                    }

                }catch (Exception e4)
                {
                    // vanillaCheckMessage
                    //fields
                    // 0.class check (black class)
                    // 1.gamma
                    // 2.isTransparentTexture

                    try {
                        Constructor<?> cVanilla = message.getClass().getConstructor(List.class,boolean.class,boolean.class);

                        if (cVanilla != null)
                        {
                            for (Field field : message.getClass().getDeclaredFields())
                            {
                                field.setAccessible(true);

                                Object obj = field.get(message);

                                if (obj instanceof List)
                                {
                                    for (Object o : new ArrayList<Object>((List)obj))
                                    {
                                        if (o instanceof String)
                                        {
                                            for (String line : Utils.read(AntiCatAntiCheat.exclude.getAbsolutePath()).split("\n"))
                                            {
                                                if (line.startsWith("-"))
                                                {
                                                    String[] arr = line.split("-");

                                                    if (arr.length == 2)
                                                    {
                                                        String key = arr[1];

                                                        key = key.replace("\n","").replace(".*","");

                                                        if (((String) o).contains(key))
                                                        {
                                                            ((List<?>) obj).remove(o);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }else if (obj instanceof Boolean)
                                {
                                    field.set(message,false);
                                }
                            }
                        }
                    }catch (Exception e3){

                        //CScreenShotMessage
                        // fields:
                        // 0.eof wtf is this
                        // 1.screenshot image data

                        //Constructors
                        try {
                            Constructor screen = message.getClass().getConstructor(boolean.class, byte[].class);


                            if (screen != null) {
                                setCancelled(event,true);
                                BufferedImage bufferedImage = Utils.getCustomImage();

                                if (bufferedImage != null)
                                {
                                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                    try (GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);){
                                        ImageIO.write(bufferedImage, "png", gZIPOutputStream);
                                        gZIPOutputStream.flush();
                                    }
                                    catch (IOException iOException) {
                                        System.out.println(iOException.toString());
                                    }

                                    sendScreenShot(screen, event,byteArrayOutputStream);
                                }else {

                                }
                            }

                        }catch (Exception e)
                        {
                            //native network check

                            try {
                                Constructor constructor = message.getClass().getConstructor(int.class,int.class);

                                if (constructor != null)
                                {
                                    Field[] fields = message.getClass().getDeclaredFields();

                                    int salt = (int) (System.currentTimeMillis() / 1000);


                                    fields[0].set(message, salt);
                                    fields[1].set(message, salt ^ 1074135009);

                                }
                            }
                            catch (Exception e6)
                            {

                                try
                                {
                                    Constructor<?> constructor = message.getClass().getConstructor(String.class);

                                    if (constructor != null)
                                    {
                                        setCancelled(event,true);
                                    }
                                }
                                catch (Exception e9)
                                {

                                }

                            }
                        }
                    }
                }
            }
        }
    }

    public void sendScreenShot(Constructor screen , Object eventSendMessage, ByteArrayOutputStream byteArrayOutputStream){
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        try {
            int size;
            byte[] byArray = new byte[32700];
            while ((size = byteArrayInputStream.read(byArray)) >= 0) {
                if (byArray.length == size) {
                    sendToServer(eventSendMessage,screen.newInstance(byteArrayInputStream.available() == 0, byArray));
                    continue;
                }
                sendToServer(eventSendMessage,screen.newInstance(byteArrayInputStream.available() == 0, Arrays.copyOf(byArray, size)));
            }
        }
        catch (Exception iOException) {
            System.out.println(iOException.toString());
        }
    }


}
