package com.kotlin.demo.collections

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/8/8 18:05
 * Description:
 */
fun main(array: Array<String>) {
    val numbers = listOf("one", "two", "three", "four")

    val plusList = numbers + "five"
    val minusList = numbers - listOf("three", "four")

    println(numbers)
    println(plusList)
    println(minusList)
}