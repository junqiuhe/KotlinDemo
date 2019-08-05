package com.kotlin.demo.other

import java.lang.IllegalArgumentException
import java.lang.NumberFormatException

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/8/5 14:36
 * Description:
 */

/**
 * 1、异常类：Kotlin中所有异常类都是 Throwable 类的子孙类。
 *
 * 2、try是一个表达式
 * try是一个表达式，即可以有一个返回值。
 *
 * 返回值是try块中的最后一个表达式或者是（所有）catch块中的最后一个表达式。
 * finally块中的内容不会影响表达式的结果。
 *
 * 3、受检的异常：Kotlin中没有受检的异常。Kotlin并不区分受检异常和未受检异常。
 *
 * 4、Nothing 类型
 *
 *
 */
fun main(array: Array<String>) {

    try {
    } catch (e: Throwable) {
        e.printStackTrace()
    }

    try {
    } finally {
    }

    //try是一个表达式。
    val a: Int? = try {
        "123ds11".toInt()
    } catch (e: NumberFormatException) {
        null
    }
    println(a)

    /**
     * throw 表达式的类型是特殊类型 Nothing. 该类型没有值，而是用于标记永远不能达到的代码位置。
     * 在代码中，可以使用Nothing来标记一个永远不会反悔的函数。
     */
//    val s = a ?: throw IllegalArgumentException("a required!")
//    println(s)

    val s1 = a ?: fail("a required")
    println(s1)

    val x = null  // x 类型为 Nothing?
    val l = listOf(null) // l 类型为List<Nothing?>
}

fun fail(message: String): Nothing {
    throw IllegalArgumentException(message)
}