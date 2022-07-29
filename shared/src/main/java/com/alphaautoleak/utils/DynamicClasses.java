package com.alphaautoleak.utils;

import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: SnowFlake
 * @Date: 2022/7/29 21:18
 */
public class DynamicClasses implements Opcodes {

    public static ClassNode fullClassProxy(ClassNode cn, String superName) {
        ClassNode clone = new ClassNode();
        clone.access = cn.access;
        clone.version = 52;
        clone.name = cn.name+"Helper";
        clone.sourceFile = cn.sourceFile;

        if (superName != null)
        {
            clone.superName = superName;
        }
        else
        {
            clone.superName = "java/lang/Object";
        }
        cn.fields.forEach(f -> clone.fields.add(new FieldNode(f.access, f.name, f.desc, null, f.value)));
        cn.methods.forEach(m -> clone.methods.add(copyMethod(m)));
        return clone;
    }


    public static MethodNode copyMethod(MethodNode original) {
        MethodNode mn = new MethodNode(original.access, original.name, original.desc, null, original.exceptions.toArray(new String[0]));
        InsnList copy = new InsnList();
        Map<LabelNode, LabelNode> labels = ASMUtils.cloneLabels(original.instructions);
        for (AbstractInsnNode ain : original.instructions.toArray()) {
            copy.add(ain.clone(labels));
        }
        if (original.tryCatchBlocks != null)
            mn.tryCatchBlocks = original.tryCatchBlocks.stream()
                    .map(tcb -> new TryCatchBlockNode(tcb.start, tcb.end, tcb.handler, tcb.type))
                    .collect(Collectors.toList());
        ASMUtils.updateInstructions(mn, labels, copy);
        mn.maxStack = 1337;
        mn.maxLocals = 1337;
        return mn;
    }






}
