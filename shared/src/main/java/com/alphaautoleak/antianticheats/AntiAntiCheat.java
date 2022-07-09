package com.alphaautoleak.antianticheats;

import com.alphaautoleak.AntiCatAntiCheat;
import com.alphaautoleak.annotation.ReflectMark;
import com.darkmagician6.eventapi.EventManager;

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

    public void onReceive(Object message ,Object event){

    }

    public void onSend(Object message ,Object event){

    }


    public void sendToServer(Object event, Object message){
        invoke(event,message,"1");
    }

    public void setCancelled(Object event, boolean stage){
        invoke(event,stage,"2");
    }

    private void invoke(Object event,Object obj,String type)
    {
        try
        {
            for (Method method : event.getClass().getMethods())
            {
                for (Annotation annotation : method.getAnnotations())
                {
                    if (annotation instanceof ReflectMark)
                    {
                        if (((ReflectMark) annotation).mark().equals(type))
                        {
                            method.invoke(event,obj);
                        }
                    }
                }
            }
        }catch (Exception e)
        {
        }
    }








}
