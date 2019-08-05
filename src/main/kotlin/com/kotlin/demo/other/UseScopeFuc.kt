package com.kotlin.demo.other.use.scope

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/8/5 15:16
 * Description:
 */

/**
 * 作用域函数：let、run、with、apply 以及 also
 *
 * 区别：
 *      以上5个函数的功能上很相似，理解它们之间的不同是非常重要的。
 *      主要有两个不同点，
 *      1、所持有的上下文对象
 *      2、返回值。
 *
 */

data class Person(var firstName: String, var age: Int) {

    fun modifyName(name: String) {
        firstName = name
    }

    fun incrementAge() {
        age++
    }
}

fun useLet() {
    Person("Alice", 20).let {
        println("before modify : $it")

        it.modifyName("Jackson")
        it.incrementAge()

        println("after modify : $it")
    }

    /**
     * 如果没有使用let，你将引入一个新的变量，然后重复利用他们
     */

    val alice = Person("Jackson", 20)
    println("before modify : $alice")

    alice.modifyName("Alice")
    alice.incrementAge()

    println("after modify : $alice")
}

fun main() {
    useLet()

    val person = Person("Alice", 20).takeIf {
        it.firstName == "Alice"
    }
    person?.firstName
}

