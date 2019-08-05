package com.kotlin.demo.reflect

import kotlin.reflect.full.memberProperties

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/7/31 15:37
 * Description:
 */

/**
 * 什么是反射? 一种在运行时动态地访问对象属性和方法的方式，而不需要事先确定这些属性是什么。
 *
 * 一、Kotlin反射API：KClass、KCallable、KFunction 和 KProperty
 *
 *  1、KClass 对应的是 java.lang.class, 可以用它列举和访问类中包含的所有声明，然后是它的超类的声明。
 *    MyClass::class ---> 会创建一个KClass的实例。
 *
 *  2、KCallable：函数和属性的超接口
 */

class Person(val name: String, val age: Int)

fun usePerson() {
    println(Person::class.simpleName)

    /**
     * 运行时获取一个对象的类，首先使用javaClass属性获取它的Java类  ----> java.lang.Object.getClass()
     * 然后访问该类的.kotlin ---> 得到一个KClass的实例
     */
    val person = Person("Alice", 29)
    val kClass = person.javaClass.kotlin  //返回一个KClass<Person>的实例

    println(kClass.simpleName)

    kClass.memberProperties.forEach {
        println(it.name)
    }
}



fun main(array: Array<String>) {
    usePerson()
}