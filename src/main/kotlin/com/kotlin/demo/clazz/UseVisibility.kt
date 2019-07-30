package com.kotlin.demo.clazz

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/7/25 9:38
 * Description: 可见性修饰符
 */

/**
 * 4种可见性修饰符：public、internal、protected、private。
 * 其中 protected 不适用于顶层修饰符
 *
 * 局部声明：局部变量、函数和类不能有可见性修饰符
 *
 * 模块：可见性修饰符 internal 意味着该成员只在相同模块内可见。一个模块是编译在一起的一套Kotlin文件。
 *
 */

//protected val num = 1 // compile error. 因为 protected 不适用于顶层修饰符

open class Outer public constructor(){

    private val a: Int = 1  //仅有在类的内部可以访问.

    protected open val b = 2  //仅有在类的内部或者在子类中可以访问

    internal val c = 3 //同一个模块可以访问。

    val d = 4 //默认为 public

    protected class Nested{
        val e: Int = 5
    }
}

class SubClass: Outer(){

    // a 不可见
    // b,c,d 可见
    // Nested 和 e 可见

    override val b: Int = 5 //此处没有显示的指定其可见性修饰符，因此它的可见性为 'protected'
}

class Unrelated(o: Outer){
    //o.a, o.b 不可见
    //o.c, o.d 可见
    //Outer.Nested 不可见, Nested.e 也不可见
}