package com.kotlin.demo.clazz

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/7/24 17:42
 * Description:
 */


/**
 * Kotlin 与 Java 8类似，既可包括抽象方法的声明，也可包含实现
 * 1、接口无法保存状态
 * 2、可以有属性，但是必须声明为抽象的或者提供访问器实现
 */
interface MyInterface{

    val prop: Int//抽象的

    val propertyWithImplementation: String //提供访问器实现
        get() = "foo"

    fun bar()  //抽象方法的声明

    fun foo(){ //默认的实现
        //do something.
        println(prop)
    }
}

class Child: MyInterface {

    override val prop: Int = 29

    override fun bar() {
    }
}

/**
 * 接口继承
 */
interface Named{
    val name: String
}

interface Person: Named {

    val firstName: String

    val lastName: String

    override val name: String get() = "$firstName , $lastName"
}

data class Employee(override val firstName: String = "",
                    override val lastName: String = "",
                    val age: Int): Person

/**
 * 解决覆盖冲突
 * 实现多个接口时，可能会遇到同一个方法继承多个实现的问题。
 * 通过 super<指定需调用的接口名>.接口方法
 */
interface A {
    fun foo() { print("A") }
    fun bar()
}

interface B {
    fun foo() { print("B") }
    fun bar() { print("bar") }
}

class C : A {
    override fun bar() { print("bar") }
}

class D : A, B {
    override fun foo() {
        super<A>.foo()
        super<B>.foo()
    }

    override fun bar() {
        super<B>.bar()
    }
}