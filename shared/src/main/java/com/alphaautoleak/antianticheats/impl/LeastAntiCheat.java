package com.alphaautoleak.antianticheats.impl;

import com.alphaautoleak.AntiCatAntiCheat;
import com.alphaautoleak.antianticheats.AntiAntiCheat;
import com.alphaautoleak.antianticheats.PacketArray;

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
public class LeastAntiCheat extends AntiAntiCheat {
    private Random random = new Random();


    public void handleScreenShot(Object event,Constructor constructor,int length){
        setCancelled(event,true); // cancelled the packet

        BufferedImage bufferedImage = Utils.getCustomImage();

        if (length < 32700)
        {
            if (bufferedImage != null)
            {


                String ct = Utils.read(AntiCatAntiCheat.text.getAbsolutePath());

                boolean isCustom = false;
                String text = "QQ951397728";

                for (String line : ct.split("\n"))
                {
                    if (!line.startsWith("#"))
                    {
                        String[] strings = line.split(">");

                        if (strings.length == 2)
                        {
                            String category = strings[0];
                            String contents = strings[1].replace("\n","");

                            if (category.equals("customText"))
                            {
                                isCustom = Boolean.parseBoolean(contents);
                            }else if (category.equals("text")){
                                text = contents;
                            }

                        }
                    }
                }
                Graphics2D graphics2D = bufferedImage.createGraphics();

                if (isCustom)
                {
                    graphics2D.drawString(text, 10, 10);
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
                    Constructor constructor = message.getClass().getConstructor(Object.class,Object.class,Object.class,Object.class,Object.class,Object.class);

                    for (Field field : message.getClass().getDeclaredFields())
                    {
                        field.setAccessible(true);

                        Object obj = field.get(message);

                        if (obj instanceof Object[][])
                        {

                            PacketArray packetArrayArray = new PacketArray((Object[][]) obj);

                            for (Object array : packetArrayArray.data)
                            {
                                PacketArray packetArray = new PacketArray((Object[]) array);

                                for (Object element : packetArray.data)
                                {

                                    if (element instanceof byte[])
                                    {

                                        int length = (int) element;

                                        handleScreenShot(event,constructor,length);

                                    }
                                }
                            }
                        }
                        else if (obj instanceof Object[])
                        {

                            Object[] objects = (Object[]) obj;

                            PacketArray packetArray = new PacketArray(objects);

                            String log = "clazz: " + message.getClass() +" - Null:"+packetArray.getNULLCount() + " - String:" + packetArray.getStringTypeCount() + " - Object:" + packetArray.getObjectTypeCount() +" - Int:"+packetArray.getIntegerTypeCount() + " - Boolean:"+packetArray.getBooleanTypeCount() + " - Byte:"+packetArray.getByteArrayTypeCount();
                            Utils.debug(log);

                            if (packetArray.getStringTypeCount() == 2 && packetArray.getIntegerTypeCount() >= 3 && packetArray.getObjectTypeCount() >= 3)
                            {


                                setCancelled(event,true); // cancelled the packet

                                //0 version number type ==> int
                                //1 dynamic class name ==> String
                                //2 system information ==> String
                                //3 network information ==> List object
                                //4 qq number ==> List long
                                //5 env arg check ==> List String
                                int channelID = (int) objects[0];

                                String className = (String) objects[1];

                                String pcInfo = (String) objects[2];

                                ArrayList<Object> networkList = (ArrayList<Object>) objects[3];

                                ArrayList<Object> newNetworkList = new ArrayList<>();


                                ArrayList<Long> qq = (ArrayList<Long>) objects[4];

                                ArrayList<String> env_arg = new ArrayList<>();

                                //remove all the arg
                                env_arg.clear();

                                // remove old networkinfo & add an fake networkinfo
                                try {
                                    Constructor<?> networkConstructor = networkList.get(0).getClass().getDeclaredConstructor(String.class, byte[].class);

                                    newNetworkList.add(networkConstructor.newInstance(
                                            "Realtek PCIe GBE Family Controller" + System.currentTimeMillis() / 1000, (new byte[]{(byte) random.nextInt(80), (byte) random.nextInt(80), (byte) random.nextInt(80), (byte) random.nextInt(80), (byte) random.nextInt(80), (byte) random.nextInt(80)})
                                            )
                                    );
                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                }


                                int i = 0;
                                //reset all the qq
                                for (Long fuck : new ArrayList<>(qq)) {
                                    qq.set(i, Utils.getLongRandom(10));
                                    i++;
                                }

                                Utils.debug(channelID+" - "+className+" - "+pcInfo+" - "+networkList+" - "+qq+" - "+env_arg);
                                sendToServer(event,constructor.newInstance(channelID, className, pcInfo, newNetworkList, qq, env_arg));

                            }
                            else if ( packetArray.getIntegerTypeCount() == 2 )
                            {
                                // native check
                                setCancelled(event,true);

                                int salt = (int) (System.currentTimeMillis() / 1000);

                                sendToServer(event,constructor.newInstance(salt,salt ^ 1074135009,"","","",""));
                            }
                            else if (packetArray.getBooleanTypeCount() == 1 && packetArray.getByteArrayTypeCount() == 1) {
                                int length = 0;
                                for (Object object : packetArray.data) {
                                    if (object instanceof byte[])
                                        length = ((byte[]) object).length;
                                }
                                handleScreenShot(event,constructor,length);
                            }
                            //(o0 instanceof ArrayList) & (o1 instanceof Boolean) & (o2 instanceof Boolean)
                            else if ( packetArray.getObjectTypeCount() == 1 && packetArray.getBooleanTypeCount() == 2 && packetArray.getNULLCount() == 13)
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
                                    for (String line : Utils.read(AntiCatAntiCheat.exclude.getAbsolutePath()).split("\n"))
                                    {
                                        if (line.startsWith("-"))
                                        {
                                            String[] arr = line.split("-");

                                            if (arr.length == 2)
                                            {
                                                String key = arr[1];

                                                key = key.replace("\n","").replace(".*","");

                                                if (clazzName.contains(key))
                                                {
                                                    ((List<?>) blackList).remove(clazzName);
                                                }
                                            }
                                        }
                                    }
                                }

                                sendToServer(event,constructor.newInstance(blackList,false,false,"","",""));

                            }

//                            packetArray.getObjectTypeCount() == 1

                            if ( (packetArray.getObjectTypeCount() == 1 && packetArray.getNULLCount() == 15) || ( packetArray.getNULLCount() == 14 && packetArray.getObjectTypeCount() == 2))
                            {
                                //0 filehash check ==> List ModInfo

                                for (Object o : objects)
                                {
                                    if (o != null)
                                    {
                                        if (o instanceof ArrayList) {
                                            ArrayList<Object> list = (ArrayList<Object>) o;

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

                            //check
                            if (packetArray.getStringTypeCount() == 2 && packetArray.getNULLCount() == 14 )
                            {
                                for (Object o : packetArray.data)
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


    public void sendScreenShot(Constructor screen , Object event, ByteArrayOutputStream byteArrayOutputStream){
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
    public void onReceive(Object message,Object event)
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
                        PacketArray packetArray = new PacketArray((Object[]) obj);


                        if (packetArray.getBooleanTypeCount() == 1 && packetArray.getIntegerTypeCount() == 1 && packetArray.getNULLCount() == 14)
                        {
                            for (Object o : packetArray.data)
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
