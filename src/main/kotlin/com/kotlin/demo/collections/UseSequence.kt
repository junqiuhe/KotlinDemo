package com.kotlin.demo.collections

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/8/8 15:00
 * Description:
 */

/**
 * 序列是Kotlin平台库另一种容器类型 --- Sequence(Sequence<T>)
 *
 * 惰性集合操作：序列
 *
 * Collection集合在进行变换的时候，如 map 和 filter时，这些函数会及早地创建中间集合。
 * 也就是说每一步的中间结果被存储在一个临时列表。
 *
 * 然而序列给了你执行这些操作的另一种选择，可以避免创建这些临时中间对象。
 *
 * 通常，需要对一个大型集合执行链式操作时要使用序列。
 */

private fun createSequence() {
    /**
     * 通过元素的方式创建序列
     */
    val numbersSequence = sequenceOf("four", "three", "two", "one")

    /**
     * 通过Iterable创建序列
     *
     * 如果你有Iterable对象（如，List或者Set），你可以调用asSequence()创建序列
     */
    val sequence = listOf("one", "two").asSequence()
    sequence.map {
    }

    /**
     * generateSequence()函数创建
     */
    generateSequence { }

    /**
     * 由组块
     */
//    kotlin.sequences.sequence {
//    }
}

/**
 * 对于集合来讲：先将集合中的所有的元素进行map操作，将结果得到的集合中的所有元素进行filter操作。
 * 而对于序列来讲：处理完第一个元素（先映射在过滤），然后再完成第二个元素的处理。
 */
private fun useSequence() {
    listOf(1, 2, 3, 4).asSequence().map {
        print("map($it) ")
        it * it
    }.filter {
        print("filter($it) ")
        it % 2 == 0
    }

    listOf(1, 2, 3, 4).asSequence().map {
        print("map($it) ")
        it * it
    }.filter {
        print("filter($it) ")
        it % 2 == 0
    }.toList()
}

fun main(array: Array<String>) {
    useSequence()
}