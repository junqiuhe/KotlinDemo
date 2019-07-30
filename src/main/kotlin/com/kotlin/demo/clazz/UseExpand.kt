package com.kotlin.demo.clazz.expand

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/7/25 9:55
 * Description:
 */

/**
 * Kotlin 中扩展是能够拓展一个类的新功能而无需继承该类或者想装饰器这样的任何类型的设计模式
 *
 * Kotlin 支持 “扩展函数” 与 "扩展属性"
 *
 * 扩展函数：需要用一个接受者类型（被扩展的类型）作为它的前缀
 */
fun main(array: Array<String>){
    val list = mutableListOf(1,2,3)
    list.swap(0,1)

    println(list)

    println("------------------------")
    printFoo(D1())
}

//扩展函数
fun <T> MutableList<T>.swap(index1: Int, index2: Int){
    val temp = this[index1]
    this[index1] = this[index2]
    this[index2] = temp
}

/**
 * 扩展是静态解析的：扩展不能真正的修改它们所扩展的类。
 * 意味着你并没有在一个类中插入新成员，仅仅是可以通过该类型的变量用 “点表达式” 调用这个新函数。
 *
 * “拓展函数” 是静态分发的，即它们不是根据接收者类型的虚方法。
 * 即 “扩展函数” 是函数调用所在的表达式类型决定的，而不是表达式运行时求值结果决定的。
 *
 * 1、扩展函数 与 成员函数一样时，这种情况调用的 成员函数；
 * 2、扩展函数 重载 成员函数时， 这种情况调用是 扩展函数。
 */
open class C1
class D1: C1()

fun C1.foo() = "c1"

fun D1.foo() = "D1"

fun printFoo(c: C1){
    println(c.foo())
}
//printFoo(D1())  --> 输出的是 C1

/**
 * 扩展属性：不能有初始化器。
 */
val <T> List<T>.lastIndex: Int get() = size - 1

/**
 * 伴生对象的扩展
 */
class MyClass{
    companion object {
    }
}
fun MyClass.Companion.foo(){
}