package com.alphaautoleak.antianticheats.impl;

import com.alphaautoleak.antianticheats.AntiAntiCheat;
import com.alphaautoleak.antianticheats.counter.WriteCounter;
import com.alphaautoleak.utils.ASMUtils;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.lang.reflect.Constructor;

/**
 * @Author: SnowFlake
 * @Date: 2022/7/26 12:30
 */
public class HighVersionAnti extends AntiAntiCheat {

    @Override
    public void onReceive(Object message, Object event) {

    }








    @Override
    public void onSend(Object message, Object event) {
        try
        {
            if (message.getClass().getCanonicalName().contains("165824") ||
                    message.getClass().getCanonicalName().contains("moe") ||
                    message.getClass().getCanonicalName().contains("minecraftforge")
            )
            {
                Constructor<?> constructor = message.getClass().getConstructor(Object.class,Object.class,Object.class,Object.class,Object.class,Object.class); // 6 input args

                ClassNode packetNode = ASMUtils.readClass(message.getClass());

                WriteCounter writeCounter = null;

                if (packetNode != null)
                {
                    for (MethodNode m : packetNode.methods)
                    {
                        if (m.desc.equals(Type.getConstructorDescriptor(constructor)))
                        {
                            writeCounter = new WriteCounter(m);
                        }
                    }
                }



                if (writeCounter != null)
                {













                }


            }
        }
        catch (Exception e)
        {
        }
    }




}
