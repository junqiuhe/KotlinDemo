package com.java.demo.clazz.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/7/31 9:40
 * Description:
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface IntValue {

    int value() default -1;

}