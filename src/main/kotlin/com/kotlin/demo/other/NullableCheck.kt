package com.kotlin.demo.other

import java.lang.Exception

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/8/5 11:46
 * Description:
 */

/**
 * 可空类型和非空类型.
 */
fun main(array: Array<String>) {

    /**
     * 1、在条件中检查 null
     */
    val b: String? = "Kotlin"
    if (b != null && b.length > 0) {
        print("String of length ${b.length}")
    } else {
        print("Empty string")
    }

    /**
     * 2. 安全调用操作符: ?.
     *
     * 如果b非空，就返回b.length.否则返回null.
     *
     * val b:String? = null
     * b?.length
     *
     * bob?.department?.head?.name //如何一个环节为null，这个链式调用就会返回null.
     */
    val a = "Kotlin"
    val nullable: String? = null

    println(a.length)
    println(nullable?.length)

    /**
     * 3. Elvis操作符:  ?:
     *
     * 如果?:左侧表达式非空，elvis操作符就返回其左侧表达式，否则返回右侧表达式。
     * 当且仅当左侧为空时，才会对右侧表达式求值.
     */
    val l: Int = if(b != null) b.length else -1
    val l1: Int = b?.length ?: -1

    /**
     * 4. !! 操作符 （非空断言运算符）
     *
     * 非空断言运算符（!!）将任何值转换成非空类型。若该值为null则抛出异常。
     */

    try{
        println(nullable!!.length)
    }catch (e: Exception){
        e.printStackTrace()
    }

    /**
     * 安全类型转换 | as? 如果类型转换错误，将会返回null...
     */
}