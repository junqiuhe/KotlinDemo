package com.kotlin.demo.`fun`

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/7/26 9:34
 * Description:
 */

/**
 * 函数类型：顶层函数、局部函数、成员函数、扩展函数、泛型函数、高阶函数和Lambada表达式、内联函数
 *
 * Kotlin中函数声明
 *
 * fun <函数名>([<函数参数>])[<: 返回值>]{
 *      //函数体
 * }
 *
 * 函数使用的是 pascal 表达法定义， name: Type；多个参数用 逗号 分割。
 *
 * 可以给参数指定默认值。
 */
fun double(x: Int = 0): Int {
    return 2 * x
}

//单表达式函数
fun sum(x: Int = 0, y: Int = 0): Int = x + y

/**
 * 可变参数的声明方法：vararg.
 */
fun <T> toList(vararg v: T): MutableList<T> {
    val list = mutableListOf<T>()
    for (ts in v) {
        list.add(ts)
    }
    return list
}

/**
 * 中缀表示法：标有 infix 关键字的函数 可以采用中缀表示法（忽略该调用的点与圆括号）
 * 条件：1、必须是成员函数或者扩展函数
 * 2、必须只有一个参数
 * 3、不得接受可变参数或者不能有默认值
 */
infix fun Int.plus(x: Int): Int {
    return this + x
}

fun usePlus(x: Int, y: Int) {
    println("$x + $y = ${x plus y}")
}

fun main(array: Array<String>) {
    //函数的使用
    println(double(1))
    println(double())

    println(sum())
    println(sum(x = 3, y = 3))

    toList(1, arrayOf(1, 2, 3, 4, 5), 2, 3)

    usePlus(x = 1, y = 2)

    for (i in 0 until 23) {
        println(i)
    }
    val it: Iterator<Int> = (0 until 23 step 2).iterator()
    while (it.hasNext()) {
        println(it.next())
    }

    val str = "Bye"
    println(str.repeat(2))

    2 repeat "Bye "
}

infix fun Int.repeat(str: String) = str.repeat(this)

