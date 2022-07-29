package com.alphaautoleak.antianticheats;

import com.alphaautoleak.AntiCatAntiCheat;
import com.alphaautoleak.annotation.ReflectMark;
import com.alphaautoleak.utils.ReflectUtilies;
import com.alphaautoleak.utils.Utils;
import com.darkmagician6.eventapi.EventManager;

import javax.swing.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * @Author: SnowFlake
 * @Date: 2022/7/8 20:43
 */
public class AntiAntiCheat {

    public AntiAntiCheat(){

    }

    public void onReceive(Object message ,Object messageHandler,Object event){




    }

    public void onSend(Object message ,Object event){
    }

    public void sendToServer(Object event, Object message){
        ReflectUtilies.invoke(event,"1",message);
    }

    public void setCancelled(Object event, boolean stage){
        ReflectUtilies.invoke(event,"2",stage);
    }










}
