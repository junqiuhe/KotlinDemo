package com.kotlin.demo.other

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/8/5 11:03
 * Description:
 */

/**
 * Kotlin中有两种类型的相等性
 * 1、结构相等（用 equals 检查）
 *      结构相等由 == （以及其否定形式 !=）操作判断
 *
 *      a == b 被翻译成 a?.equals(b) ?: (b === null)
 *
 * 2、引用相等（两个引用指向同一对象）
 *      引用相等由  === （以及其否定形式 !==） 操作判断。
 *      a === b当且仅当 a与b指向同一个对象时求值为true。
 *
 *      对于运行时表示为原生类型的值（如Int）， === 检测等价于 == 检测
 *
 * 3、浮点数比较
 *      当其中的操作数 a 与 b都是静态已知的Float 或 Double 或者 它们对应的可控类型。
 *      两数字所形成的的操作或区间遵循IEEE 754浮点运算标准
 */
fun main(array: Array<String>) {

    val a = 123456789
    val b = 123456789
    println(a === b)

    val person = Person(name = "Alice", age = 29)
    val person1 = Person(name = "Alice", age = 29)

    println(person == person1)   // true.
    println(person === person1) // false

}