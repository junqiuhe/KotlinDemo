package com.kotlin.demo.other

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/7/29 11:36
 * Description:
 */

/**
 * 解构声明
 *
 * 有时候把一个对象 解构 成许多变量很方便，如
 *
 * val (name. age) = person
 *
 * 这种语法称为 “解构声明”。一个解构声明同时可以创建多个变量。
 *
 * 一个解构声明会被编译成如下代码：
 * val name = person.component1()
 * val age = person.component2()
 *
 * 任何表达式都可以出现在解构声明的右侧，只要可以对它调用所需数量的 component函数即可。
 *
 * 数据类自动声明 componentN() 函数。所以可以用解构声明。
 *
 * 你可以对 lambda表达式参数使用解构声明语法。如果 lambda表达式 具有 Pair类型（或者 Map.entry 或者任何其他具有相应的 componentN 函数类型）
 * 的参数，那么可以通过将它们放在括号中来引入多个新参数来取代单个新参数。
 */
data class Result(val result: Int, val status: Boolean)

fun function(): Result{
    //... do something ...

    return Result(1, true)
}

fun main(array: Array<String>) {

    val varargs = "Jackson" to "hello world"

    val(key, value) = varargs
    println("key : $key, value: $value")

    val (result, status) = function()
    println("result: $result, status: $status")

    mapOf<String, String>().mapValues { (_, value) -> value }
}