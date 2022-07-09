package com.alphaautoleak.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * @Author: SnowFlake
 * @Date: 2022/7/9 22:30
 */
@Target(ElementType.METHOD)
public @interface ReflectMark {

    String mark() default "";

}
