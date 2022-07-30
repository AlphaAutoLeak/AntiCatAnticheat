package com.alphaautoleak.analyzer;

import org.apache.commons.io.IOUtils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

import java.io.File;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.*;

/**
 * @Author: SnowFlake
 * @Date: 2022/7/30 13:35
 */
public class JarAnalyzer {

    public final HashMap<String, ClassNode> classes = new HashMap<>();
    private final Map<String, byte[]> resources = new HashMap<>();

    // fix
    public void exportJar() {
        //remove the cat anti cheat mf file jarsigner check
    }

    // analyse the class
    public void analyse(){

    }



    public void loadJar(File file) {
        try {
            JarFile inputJar = new JarFile(file);
            for (Enumeration<JarEntry> iter = inputJar.entries(); iter.hasMoreElements(); ) {
                JarEntry entry = iter.nextElement();
                try (InputStream in = inputJar.getInputStream(entry)) {
                    if (entry.getName().endsWith(".class")) {
                        ClassReader reader = new ClassReader(in);
                        ClassNode classNode = new ClassNode();
                        reader.accept(classNode, 0);
                        classes.put(classNode.name, classNode);
                    } else if (!entry.isDirectory()) {
                        resources.put(entry.getName(), IOUtils.toByteArray(in));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
