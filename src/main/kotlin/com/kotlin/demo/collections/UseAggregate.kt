package com.kotlin.demo.collections

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/8/9 10:46
 * Description:
 */

fun main(array: Array<String>) {

    var numbers = listOf(6, 42, 10, 4)
    println("Count: ${numbers.count()}")
    println("Max: ${numbers.max()}")
    println("Min: ${numbers.min()}")
    println("Average: ${numbers.average()}")  //求平均值
    println("Sum: ${numbers.sum()}")

    numbers = listOf(5, 42, 10, 4)
    println(numbers.minBy { it % 3 })


    /**
     * reduce: 第一个元素作为初始值。
     * fold: 指定其初始值.
     */
    println(numbers.reduce { sum, element -> sum + element })
    println(numbers.fold(0) { sum, element ->
        sum + element
    })
}