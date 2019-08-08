package com.kotlin.demo.collections

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/8/8 18:09
 * Description:
 */

/**
 * 使用分组
 *  groupBy() 接收一个lambda表达式，返回一个Map.
 *
 *  在这个Map中，lambda表达式的结果作为key. 对应的值是返回此结果的元素列表。
 *
 *  groupingBy: 如果要对元素进行分组，然后一次对所有组应用一个操作
 */
fun main(array: Array<String>) {
    var numbers = listOf("one", "two", "three", "four", "five")
    println(numbers.groupBy { it.first().toUpperCase() })
    println(numbers.groupBy({ keySelector ->
        keySelector.first()
    }, { value ->
        value.toUpperCase()
    }))

    numbers = listOf("three", "four", "five", "one", "two")
    println(numbers.groupBy { it.length > 3 })

    println(numbers.groupingBy { it.first() }.eachCount())
}