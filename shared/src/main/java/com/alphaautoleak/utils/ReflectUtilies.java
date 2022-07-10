package com.alphaautoleak.utils;

import com.alphaautoleak.annotation.ReflectMark;
import sun.misc.Unsafe;
import sun.reflect.Reflection;
import sun.security.util.SecurityConstants;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * @Auther: SnowFlake
 * @Date: 2022/1/30 01:35
 */
public class ReflectUtilies {

    private static sun.misc.Unsafe unsafe = null;

    public static void invoke(Class<?> clazz , Object instance,String type,Object... objects)
    {
        try
        {
            for (Method method : clazz.getMethods())
            {

                ReflectMark annotation = method.getAnnotation(ReflectMark.class);

                if (annotation != null)
                {
                    if (annotation.id().equals(type))
                    {
                        method.invoke(instance,objects);
                    }
                }
            }
        }catch (Exception e)
        {
        }
    }

    public static void invoke(Object instance,String type,Object obj)
    {
        try
        {
            for (Method method : instance.getClass().getMethods())
            {

                ReflectMark annotation = method.getAnnotation(ReflectMark.class);

                if (annotation != null)
                {
                    if (annotation.id().equals(type))
                    {
                        method.invoke(instance,obj);
                    }
                }
            }
        }catch (Exception e)
        {
        }
    }


    public static void modify(String src, String to) {
        try {
            Field value = setAccessible(String.class.getDeclaredField("value"));
            set(src, value, value.get(to));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T extends AccessibleObject> T setAccessible(T t) {
        t.setAccessible(true);
        return t;
    }


    public static void defineClass(String name , byte[] classByte){
        unsafe.defineClass(name,classByte,0 ,classByte.length,Thread.currentThread().getContextClassLoader(),null);
    }

    static {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe = (Unsafe) f.get(null);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void set(Object obj, Field field, Object value) throws Exception {
        unsafe.putObject(obj, unsafe.objectFieldOffset(field), value);
    }
    public static boolean contains(String target,CharSequence str) {
        return target.indexOf(str.toString()) > -1;
    }

    public static void setStatic(Field field, Object value) throws Exception {
        unsafe.putObject(unsafe.staticFieldBase(field), unsafe.staticFieldOffset(field), value);
    }

    public static void removeFinal(Field field, Object newValue)  {
        try {

            field.setAccessible(true); // 如果field为private,则需要使用该方法使其可被访问

            Field modifersField = Field.class.getDeclaredField("modifiers");
            modifersField.setAccessible(true);
            // 把指定的field中的final修饰符去掉
            modifersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

            field.set(null, newValue); // 为指定field设置新值
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public static void setSecurityManager(SecurityManager securityManager) {
        if (Reflection.getCallerClass(2) != RedirectUtils.class){
            return;
        }
        try {
            SecurityManager sm = System.getSecurityManager();

            if ((sm != null) && (sm.getClass().getClassLoader() != null)) {
                AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
                    sm.getClass().getProtectionDomain().implies(SecurityConstants.ALL_PERMISSION);
                    return null;
                });
            }

            Method reflectionDataMethod = Class.class.getDeclaredMethod("reflectionData");
            reflectionDataMethod.setAccessible(true);
            Object reflectionData = reflectionDataMethod.invoke(System.class);

            Field[] res = null;
            if (reflectionData != null) {
                Field field = reflectionData.getClass().getDeclaredField("declaredFields");
                field.setAccessible(true);
                res = (Field[]) field.get(reflectionData);
            }

            if(res == null) {
                Method method = Class.class.getDeclaredMethod("getDeclaredFields0", boolean.class);
                method.setAccessible(true);
                res = (Field[]) method.invoke(System.class, false);
            }

            for(Field re : res) {
                if(re.getName().equals("security")) {
                    re.setAccessible(true);
                    re.set(null, securityManager);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
