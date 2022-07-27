package com.alphaautoleak.antianticheats.counter;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

/**
 * @Author: SnowFlake
 * @Date: 2022/7/28 01:11
 */
public class WriteCounter
{
    public MethodNode methodNode;

    public int shortCount;
    public int stringCount;
    public int booleanCount;
    public int integerCount;
    public int objectCount;
    public int byteCount;

    public int byteArrayCount;
    public int longCount;



    public WriteCounter(MethodNode methodNode) {
        this.methodNode = methodNode;

        for (AbstractInsnNode abstractInsnNode : methodNode.instructions.toArray())
        {
            if (abstractInsnNode instanceof MethodInsnNode)
            {
                MethodInsnNode writeNode = (MethodInsnNode) abstractInsnNode;

                if (writeNode.name.equals("writeUTF8String"))
                {
                    this.stringCount++;
                }

                if (writeNode.name.equals("writeShort"))
                {
                    this.shortCount++;
                }

                if (writeNode.name.equals("writeInt"))
                {
                    this.integerCount++;
                }

                if (writeNode.name.equals("writeBoolean"))
                {
                    this.booleanCount++;
                }

                if (writeNode.name.equals("writeBytes"))
                {
                    this.byteArrayCount++;
                }

                if (writeNode.name.equals("writeLong"))
                {
                    this.longCount++;
                }
                if (writeNode.name.equals("writeByte"))
                {
                    this.byteCount++;
                }
            }
        }
    }


}
