package com.alphaautoleak.utils;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.*;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: SnowFlake
 * @Date: 2022/7/9 21:51
 */
public class ASMUtils {


    public static void updateInstructions(MethodNode m, Map<LabelNode, LabelNode> labels, InsnList rewrittenCode) {
        m.instructions.clear();
        m.instructions = rewrittenCode;
        if (m.tryCatchBlocks != null) {
            m.tryCatchBlocks.forEach(tcb -> {
                tcb.start = labels.get(tcb.start);
                tcb.end = labels.get(tcb.end);
                tcb.handler = labels.get(tcb.handler);
            });
        }
        if (m.localVariables != null) {
            m.localVariables.forEach(lv -> {
                lv.start = labels.get(lv.start);
                lv.end = labels.get(lv.end);
            });
        }
        m.visibleLocalVariableAnnotations = null;
        m.invisibleLocalVariableAnnotations = null;
    }

    public static Map<LabelNode, LabelNode> cloneLabels(InsnList insns) {
        HashMap<LabelNode, LabelNode> labelMap = new HashMap<>();
        for (AbstractInsnNode insn = insns.getFirst(); insn != null; insn = insn.getNext()) {
            if (insn.getType() == AbstractInsnNode.LABEL) {
                labelMap.put((LabelNode) insn, new LabelNode());
            }
        }
        return labelMap;
    }
    public static ClassNode readClass(Class<?> clazz){
        try {
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
            return classNode;
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }


    public static boolean isClass(byte[] bytes){
        if ((bytes[0] & 0xFF) == 0xCA && (bytes[1] & 0xFF) == 0xFE && (bytes[2] & 0xFF) == 0xBA && (bytes[3] & 0xFF) == 0xBE)
        {
            return true;
        }
        return false;
    }

}
