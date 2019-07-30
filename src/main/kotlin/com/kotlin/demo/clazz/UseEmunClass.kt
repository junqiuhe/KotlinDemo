package com.kotlin.demo.clazz

import java.util.function.BiFunction

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/7/25 16:06
 * Description:
 */

/**
 * 枚举类
 * 最基本的用法是 实现类型安全的枚举
 */
enum class Direction {
    NORTH, SOUTH, WEST, EAST
}

//初始化
enum class Color(val rgb: Int) {
    RED(0XFF0000),
    GREEN(0X00FF00),
    BLUE(0X0000FF)
}

/**
 * 匿名类
 * 枚举常量也可声明自己的匿名类
 */
enum class ProtocolState(val enumName: String) {

    WAITING("waiting") {
        override fun signal(): String = "waiting"

        override fun enumName(): String = enumName
    },

    TALKING("talking") {
        override fun signal(): String = "talking"

        override fun enumName(): String {
            return enumName
        }
    };

    abstract fun signal(): String

    abstract fun enumName(): String
}

/**
 * 在枚举类中实现接口
 */
enum class IntArithmetics : BiFunction<Int, Int, Int> {

    PLUS {
        override fun apply(t: Int, u: Int): Int = t + u
    },

    TIMES {
        override fun apply(t: Int, u: Int): Int = t * u
    };

    companion object {
        fun printlnAllValues(): String{
            val values = enumValues<IntArithmetics>()
            return values.joinToString(separator = ",") { it.name }
        }
    }
}

fun main(array: Array<String>) {
    val color = Color.BLUE
    color.rgb

    println(IntArithmetics.PLUS.apply(1, 2))
    println(IntArithmetics.TIMES.apply(1, 2))

    println(IntArithmetics.printlnAllValues())
}