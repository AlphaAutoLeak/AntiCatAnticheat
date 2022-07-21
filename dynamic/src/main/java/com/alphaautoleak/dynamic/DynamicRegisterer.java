package com.alphaautoleak.dynamic;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @Author: SnowFlake
 * @Date: 2022/7/14 11:01
 */
public class DynamicRegisterer {

    public DynamicRegisterer(){
        registerNativeInvoke();
    }
    public static boolean isInteger(AbstractInsnNode ain)
    {
        if (ain == null) return false;
        if((ain.getOpcode() >= Opcodes.ICONST_M1
                && ain.getOpcode() <= Opcodes.ICONST_5)
                || ain.getOpcode() == Opcodes.SIPUSH
                || ain.getOpcode() == Opcodes.BIPUSH)
            return true;
        if(ain instanceof LdcInsnNode)
        {
            LdcInsnNode ldc = (LdcInsnNode)ain;
            if(ldc.cst instanceof Integer)
                return true;
        }
        return false;
    }

    public static int getIntValue(AbstractInsnNode node)
    {
        if(node.getOpcode() >= Opcodes.ICONST_M1
                && node.getOpcode() <= Opcodes.ICONST_5)
            return node.getOpcode() - 3;
        if(node.getOpcode() == Opcodes.SIPUSH
                || node.getOpcode() == Opcodes.BIPUSH)
            return ((IntInsnNode)node).operand;
        if(node instanceof LdcInsnNode)
        {
            LdcInsnNode ldc = (LdcInsnNode)node;
            if(ldc.cst instanceof Integer)
                return (int)ldc.cst;
        }
        return 0;
    }

    public static AbstractInsnNode getIntInsn(int number) {
        if (number >= -1 && number <= 5)
            return new InsnNode(number + 3);
        else if (number >= -128 && number <= 127)
            return new IntInsnNode(Opcodes.BIPUSH, number);
        else if (number >= -32768 && number <= 32767)
            return new IntInsnNode(Opcodes.SIPUSH, number);
        else
            return new LdcInsnNode(number);
    }

    public static int verifyCode = 0;

    public static void registerNativeInvoke(){
        try {
            Class<?> clazz = Class.forName("moe.catserver.mc.cac.NativeLoader");

            ClassNode classNode = new ClassNode();

            Method m = null;
            try {
                m = ClassReader.class.getDeclaredMethod("readClass", InputStream.class , boolean.class);
            }catch (NoSuchMethodException e)
            {
                m = ClassReader.class.getDeclaredMethod("a", InputStream.class , boolean.class);
            }
            m.setAccessible(true);

            byte[] bytes = (byte[]) m.invoke(null , Thread.currentThread().getContextClassLoader().getResourceAsStream(clazz.getName().replace('.', '/') + ".class"),true);
            ClassReader classReader = new ClassReader(bytes);
            classReader.accept(classNode,0);

            //modify the byte code
            for (MethodNode methodNode : classNode.methods) {
                if ( !(methodNode.instructions.size() > 2)) continue;
                for (AbstractInsnNode ain : methodNode.instructions.toArray()) {
                    if (isInteger(ain) && ain.getNext() != null && isInteger(ain.getNext())
                            && ain.getNext().getNext() != null
                            && ain.getNext().getNext().getOpcode() == Opcodes.SWAP
                            && ain.getNext().getNext().getNext() != null
                            && ain.getNext().getNext().getNext().getOpcode() == Opcodes.DUP_X1
                            && ain.getNext().getNext().getNext().getNext() != null
                            && ain.getNext().getNext().getNext().getNext().getOpcode() == Opcodes.POP2
                            && ain.getNext().getNext().getNext().getNext().getNext() != null
                            && isInteger(ain.getNext().getNext().getNext().getNext().getNext())
                            && ain.getNext().getNext().getNext().getNext().getNext().getNext() != null
                            && ain.getNext().getNext().getNext().getNext().getNext().getNext().getOpcode() == Opcodes.IXOR) {

                        int result = getIntValue(ain) ^ getIntValue(ain.getNext().getNext().getNext().getNext().getNext());
                        methodNode.instructions.remove(ain.getNext().getNext().getNext().getNext().getNext().getNext());
                        methodNode.instructions.remove(ain.getNext().getNext().getNext().getNext().getNext());
                        methodNode.instructions.remove(ain.getNext().getNext().getNext().getNext());
                        methodNode.instructions.remove(ain.getNext().getNext().getNext());
                        methodNode.instructions.remove(ain.getNext().getNext());
                        methodNode.instructions.remove(ain.getNext());
                        methodNode.instructions.set(ain, getIntInsn(result));
                    }
                }
            }

            for (MethodNode methodNode2 : classNode.methods)
            {
                for (AbstractInsnNode abstractInsnNode : methodNode2.instructions.toArray())
                {

                    // invokestatic int NativeLoader.BiePoLe5555()
                    // ldc Integer a number
                    // iadd
                    // invokestatic Integer Integer.valueOf(int)
                    // ldc Integer a number
                    // invokestatic Integer Integer.valueOf(int)
                    // invokestatic boolean Objects.equals(Object, Object)
                    if (abstractInsnNode instanceof MethodInsnNode &&
                        isInteger(abstractInsnNode.getNext()) &&
                        abstractInsnNode.getNext().getNext().getOpcode() == Opcodes.IADD &&
                        abstractInsnNode.getNext().getNext().getNext() instanceof MethodInsnNode &&
                        isInteger(abstractInsnNode.getNext().getNext().getNext().getNext()) &&
                        abstractInsnNode.getNext().getNext().getNext().getNext().getNext() instanceof MethodInsnNode &&
                        abstractInsnNode.getNext().getNext().getNext().getNext().getNext().getNext() instanceof MethodInsnNode
                    )
                    {
                        int toAdd = getIntValue(abstractInsnNode.getNext());
                        int result = getIntValue(abstractInsnNode.getNext().getNext().getNext().getNext());
                        DynamicRegisterer.verifyCode = result - toAdd;
                    }
                    // invokestatic int NativeLoader.func_1_1()
                    // invokestatic Integer Integer.valueOf(int)
                    // ldc Integer a number
                    // invokestatic Integer Integer.valueOf(int)
                    // invokestatic boolean Objects.equals(Object, Object)
                    else if (abstractInsnNode instanceof MethodInsnNode &&
                        abstractInsnNode.getNext() instanceof MethodInsnNode &&
                        isInteger(abstractInsnNode.getNext().getNext()) &&
                        abstractInsnNode.getNext().getNext().getNext() instanceof MethodInsnNode &&
                        abstractInsnNode.getNext().getNext().getNext().getNext() instanceof MethodInsnNode
                    )
                    {
                        DynamicRegisterer.verifyCode = getIntValue(abstractInsnNode.getNext().getNext());
                    }
                }
            }

            String owner = clazz.getName().replace(".","./");
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods)
            {
                method.setAccessible(true);
                if (Modifier.toString(method.getModifiers()).contains("native"))
                {
                    Class<?> type = method.getReturnType();

                    if (type.equals(int.class))
                    {
                        if (method.getParameterCount() == 1)
                        {
                            //register the network method
                            registerFakeNative(verifyCode,method.getName(),"(I)I");
                        }
                        else if (method.getParameterCount() == 0)
                        {
                            //register the init method
                            registerFakeNative(verifyCode,method.getName(),"()I");
                        }
                    }else if (type.equals(void.class))
                    {
                        //old version register
                        registerFakeNative(verifyCode,method.getName(),"()V");
                    }
                    else if (type.equals(boolean.class))
                    {
                        registerFakeNative(verifyCode,method.getName(),"()Z");
                    }
                    else if (type.getName().contains("catserver"))
                    {
                        registerFakeNative(verifyCode,method.getName(),"(Lmoe/catserver/mc/cac/NativeServerDynamicSandboxMessage;)Lmoe/catserver/mc/cac/NativeClientReportMessage;");

                    }
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static native void registerFakeNative(int verifyCode,String name,String desc);

}
