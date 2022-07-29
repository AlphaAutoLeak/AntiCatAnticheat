package com.alphaautoleak.asm;

import com.alphaautoleak.events.EventReceiveMessage;
import com.alphaautoleak.events.EventSendMessage;
import com.darkmagician6.eventapi.EventManager;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.util.EnumMap;
import java.util.function.BiConsumer;

/**
 * @Author: SnowFlake
 * @Date: 2022/6/3 18:02
 */
public class ClassTransformer implements IClassTransformer, Opcodes
{

    @Override
    public byte[] transform(String name, String transformedName, byte[] classByte)
    {

        // 1.12.2
        if (name.equals("net.minecraftforge.fml.common.network.simpleimpl.SimpleChannelHandlerWrapper"))
        {
            return transformMethods(classByte,this::transformSimpleChannelHandlerWrapper1_12_2);
        }
        if (name.equals("net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper"))
        {
            return transformMethods(classByte,this::transformSimpleNetworkWrapper1_12_2);
        }

        return classByte;
    }


    public void transformSimpleNetworkWrapper1_12_2(ClassNode classNode, MethodNode methodNode) {
        if (methodNode.name.equalsIgnoreCase("sendToServer")) {

            InsnList insnList = new InsnList();
            insnList.add(new VarInsnNode(ALOAD,1));

            insnList.add(new VarInsnNode(ALOAD,0));
            insnList.add(new FieldInsnNode(GETFIELD, "net/minecraftforge/fml/common/network/simpleimpl/SimpleNetworkWrapper", "channels", "Ljava/util/EnumMap;"));

            insnList.add(new MethodInsnNode(INVOKESTATIC, Type.getInternalName(ClassTransformer.class), "simpleNetworkWrapperHook", "(Ljava/lang/Object;Ljava/util/EnumMap;)Z", false));
            LabelNode labelNode = new LabelNode();
            insnList.add(new JumpInsnNode(IFEQ,labelNode));
            insnList.add(new InsnNode(RETURN));
            insnList.add(labelNode);
            insnList.add(new FrameNode(F_SAME, 0, null, 0, null));
            methodNode.instructions.insert(insnList);

        }
    }

    public void transformSimpleChannelHandlerWrapper1_12_2(ClassNode classNode, MethodNode methodNode) {
        if (methodNode.name.equalsIgnoreCase("channelRead0")) {

            for (FieldNode fieldNode : classNode.fields)
            {
                if (fieldNode.desc.contains("IMessageHandler"))
                {

                    InsnList insnList = new InsnList();

                    insnList.add(new VarInsnNode(ALOAD,2));

                    insnList.add(new VarInsnNode(ALOAD,0));
                    insnList.add(new FieldInsnNode(GETFIELD, classNode.name, fieldNode.name, fieldNode.desc));

                    insnList.add(new MethodInsnNode(INVOKESTATIC, Type.getInternalName(ClassTransformer.class), "simpleChannelHandlerWrapper", "(Ljava/lang/Object;Ljava/lang/Object;)Z", false));
                    LabelNode labelNode = new LabelNode();
                    insnList.add(new JumpInsnNode(IFEQ,labelNode));
                    insnList.add(new InsnNode(RETURN));
                    insnList.add(labelNode);
                    insnList.add(new FrameNode(F_SAME, 0, null, 0, null));
                    methodNode.instructions.insert(insnList);

                }
            }

        }
    }


    public static boolean simpleChannelHandlerWrapper(Object packet , Object messageHandler){
        EventReceiveMessage eventReceiveMessage = new EventReceiveMessage(packet,messageHandler);
        EventManager.call(eventReceiveMessage);
        return eventReceiveMessage.isCancelled();
    }


    public static boolean simpleNetworkWrapperHook(Object object, EnumMap enumMap){
        EventSendMessage eventSendMessage = new EventSendMessage(object,enumMap);
        EventManager.call(eventSendMessage);
        return eventSendMessage.isCancelled();
    }


    private byte[] transformMethods(byte[] bytes, BiConsumer<ClassNode, MethodNode> transformer) {
        ClassReader classReader = new ClassReader(bytes);
        ClassNode classNode = new ClassNode();
        classReader.accept(classNode, 0);
        classNode.methods.forEach(m ->
                transformer.accept(classNode, m)
        );
        ClassWriter classWriter = new ClassWriter(0);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }

}
