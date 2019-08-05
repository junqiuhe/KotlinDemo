package com.kotlin.demo.other

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/8/5 11:26
 * Description:
 */

/**
 * Kotlin允许我们为自己的类型提供预定义的一组操作符的实现。这些操作符有固定的的符号（如+或*）和固定的优先级
 *
 * 为了实现这样的操作符，我们为相应的类型（二元操作符左侧的类型，一元操作符的参数类型）
 * 提供一个固定名称的 成员函数 或 扩展哈数。 需要用到 operator 修饰符标记
 *
 * demo如下：
 */

data class Point(val x: Int, val y: Int)

//定义一元操作符
operator fun Point.unaryMinus() = Point(-x, -y)

//定义二元操作符
data class Counter(val dayIndex: Int){
    operator fun plus(increment: Int): Counter{
        return Counter(dayIndex + increment)
    }
}

fun main(array: Array<String>) {
    val point = Point(10, 20)
    println(-point)

    var counter = Counter(0)
    for(index in 0..9){
        counter += 1
    }
    println(counter)
}