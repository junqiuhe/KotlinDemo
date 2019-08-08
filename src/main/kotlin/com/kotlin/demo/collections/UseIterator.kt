package com.kotlin.demo.collections

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/8/8 11:01
 * Description:
 */

/**
 * 遍历集合的方式
 *
 *  1、利用迭代器遍历集合元素
 *  2、for loop
 *  3、forEach方法
 */
private fun useIterator() {
    val numbers = listOf("one", "two")

    val iterator = numbers.iterator()
    while (iterator.hasNext()) {
        println(iterator.next())
    }
}

private fun useForLoop() {
    val numbers = listOf("one", "two")
    for (item in numbers) {
        println(item)
    }
}

private fun useForEach() {
    val numbers = listOf("one", "two")
    numbers.forEach {
        println(it)
    }
}

/**
 * 对于list集合，提供了ListIterator迭代器
 * 具有方向性: forwards and backwards
 */
private fun useListIterator() {
    val numbers = listOf("one", "two", "three", "four")
    val listIterator = numbers.listIterator()
    while (listIterator.hasNext()) {
        println("Index: ${listIterator.nextIndex()} Value: ${listIterator.next()}")
    }

    println("iterating backwards:")
    while (listIterator.hasPrevious()) {
        println("Index: ${listIterator.previousIndex()} Value: ${listIterator.previous()}")
    }
}

/**
 * 可变迭代器。MutableIterator : Iterator，添加了 remove方法
 */

private fun useMutableIterator() {
    val numbers = mutableListOf("one", "two", "three", "four")

    val mutableIterator = numbers.iterator()
    while (mutableIterator.hasNext()) {
        if (mutableIterator.next() == "one") {
            mutableIterator.remove()
            break
        }
    }

    println(numbers)
}

fun main(array: Array<String>) {
    useListIterator()
    useMutableIterator()
}