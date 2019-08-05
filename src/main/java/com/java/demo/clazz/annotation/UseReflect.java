package com.java.demo.clazz.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/7/31 9:45
 * Description:
 */
public class UseReflect {

    public static void main(String[] args) {
        try {
            Person person = initPersonByReflect(Person.class);
            System.out.println(person);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static <T> T initPersonByReflect(Class<T> clazz) throws Exception {
        Constructor<?> constructor = clazz.getConstructor();

        T instance = (T) constructor.newInstance();

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {

            Annotation[] annotations = field.getAnnotations();

            if(annotations != null && annotations.length > 0){

                for(Annotation annotation : annotations){

                    Method targetMethod = findTargetMethod(field.getName(), clazz);
                    if(targetMethod == null){
                        continue;
                    }

                    if(annotation instanceof IntValue){
                        targetMethod.invoke(instance, ((IntValue) annotation).value());

                    }else if(annotation instanceof StringValue){
                        targetMethod.invoke(instance, ((StringValue) annotation).value());
                    }
                }
            }
        }

        return instance;
    }

    private static Method findTargetMethod(String fieldName, Class<?> clazz){

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("set");
        stringBuilder.append(fieldName.substring(0, 1).toUpperCase());
        stringBuilder.append(fieldName, 1, fieldName.length());


        Method[] methods = clazz.getMethods();
        for(Method method : methods){
            if(method.getName().equals(stringBuilder.toString())){
                return method;
            }
        }
        return null;
    }
}