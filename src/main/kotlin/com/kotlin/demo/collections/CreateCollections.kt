package com.kotlin.demo.collections

import java.util.*

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/8/7 9:44
 * Description:
 */


/**
 * Kotlin 标准库提供了基本集合类型的实现：Set、List以及Map，一对接口代表每种集合类型
 *  1、一个 “只读接口”，提供访问集合元素的操作。（是型变的）
 *  2、一个 “可变接口”，通过写操作扩展相应的只读接口，添加，删除以及更新 （不型变）
 */

/**
 * 创建集合的方式
 *  1、利用标准库函数创建，如：val numberList = listOf<String>("Jackson")
 *  2、利用具体类型的构造函数，如：val list = List<String>()
 */
fun createCollections() {

    //利用Kotlin标准库函数创建
    val numberList = listOf<String>("Jackson")
    mutableListOf<String>()

    //采用具体类型的构造函数
    val list = ArrayList<String>()
    list.add("Jackson")

    val linkedList = LinkedList<String>()
    linkedList.add("Jackson")
}

data class Customer(var name: String)

fun useCollectionCopy() {
    /**
     * 利用toList()、toMutableList()创建一个具有相同元素的新集合，
     * 如果在源集合中添加、删除，则不会影响副本。
     *
     * 但是如果修改集合中的元素，其它副本中的元素也将被修改。
     */
    val sourceList = mutableListOf(1, 2, 3)
    val copyList = sourceList.toMutableList()
    val readOnlyCopyList = sourceList.toList()

    sourceList.add(4)
    println("copy size : ${copyList.size}")

    println("read only copy size : ${readOnlyCopyList.size}")


    /**
     * 标准库中的集合复制操作创建了具有相同元素引用的 “浅复制” 集合。
     * 因此对集合中元素的更改会体现在其它副本中。
     *
     * 浅复制 与 深复制
     *
     * https://cloud.tencent.com/developer/article/1343368
     *
     */
    val source = mutableListOf<Customer>()
    source.add(Customer(name = "Jackson"))
    source.add(Customer(name = "zhangsan"))
    source.add(Customer(name = "lisi"))

    println("source: $source")
    println("---------------------------------")

    val targetList = source.toList()
    source[0].name = "wangwu"

    println("source: $source")
    println("target: $targetList")


    /**
     * 集合的初始化可用于限制其可变性。
     */
    val s = mutableListOf(1, 2, 3)
    val referenceList: List<Int> = s
//    referenceList.add(4)
}

fun operatorCollections() {
    //过滤
    val numbers = listOf("One", "Two", "Three", "Four")
    println(numbers.filter { it.length > 3 })

    //转换
    val result = numbers.map {
        val num = when (it) {
            "One" -> 1
            "Two" -> 2
            "Three" -> 3
            "Four" -> 4
            else -> -1
        }
        num
    }
    println(result)
}

fun main(array: Array<String>) {
    useCollectionCopy()
    operatorCollections()
}