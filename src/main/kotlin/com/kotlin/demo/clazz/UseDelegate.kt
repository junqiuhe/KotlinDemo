package com.kotlin.demo.clazz

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/7/24 17:01
 * Description:
 */

/**
 * 其实就是代理模式。
 * “委托模式” 已经证明是实现继承的一个很好的替代方式，而Kotlin可以零样板代码地原生支持它。
 */
interface Base{
    fun printMessage()
    fun printMessageLine()
}

class BaseImpl(val x: Int): Base {
    override fun printMessage() {
        print(x)
    }

    override fun printMessageLine() {
        println(x)
    }
}

/**
 * "覆盖由委托实现的接口成员"
 * 编译器会使用 override 覆盖的实现，而不是委托对象中的。
 * 因此下面的 printMessage 将输出 "abc".
 */
class Derived(b: Base) : Base by b

fun main(args: Array<String>){
    val base = BaseImpl(10)
    Derived(base).printMessageLine()

    val itemManager = ItemManager(mutableListOf())

    val itemAdapter = ItemAdapter(itemManager)

    itemAdapter.add(Item())

    itemManager.add(Item())
}

class ItemAdapter(private val itemManager: ItemManager): MutableList<Item> by itemManager

class ItemManager(private val list: MutableList<Item>) : MutableList<Item> by list{

    override fun add(element: Item): Boolean {
        println("add element success...")
        list.add(element)
        return true
    }
}

class Item