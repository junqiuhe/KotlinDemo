package com.kotlin.demo.collections

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/8/9 9:18
 * Description:
 */

/**
 * 取集合中的一部分
 */
fun main(array: Array<String>) {
    /**
     * slice.
     */
    val numbers = listOf("one", "two", "three", "four", "five", "six")
    println(numbers.slice(indices = 1..3))
    println(numbers.slice(indices = 0..4 step 2))
    println(numbers.slice(indices = setOf(3, 5, 0)))

    println("-------------use take/drop------------")

    /**
     * take / drop / takeLast / dropLast
     * takeWhile / dropWhile / takeLastWhile / dropLastWhile：可指定lambda表达式，定制取出的值。
     */
    println(numbers.take(3)) //取前三个
    println(numbers.takeLast(3)) //取后三个

    //取出指定的元素，还剩余的元素
    println(numbers.drop(1))  //取除给定数量的第一个或最后一个元素外的所有元素，
    println(numbers.dropLast(5))

    println("-------------use takeWhile/dropWhile------------")
    println(numbers.takeWhile { !it.startsWith('f') })
    println(numbers.takeLastWhile { it != "three" })

    println(numbers.dropWhile { it.length == 3 }) //[three, four, five, six]
    println(numbers.dropLastWhile { it.contains('i') }) //[one, two, three, four]

    /**
     * use chunked.
     * 指定给定的size组成一组。
     */
    println("-------------use chunked------------")

    val ints = (0..13).toList()
    println(ints.chunked(3))
    println(ints.chunked(3) { it.sum() })

    /**
     * use windowed
     */
    println("-------------use windowed------------")
    val strings = listOf("one", "two", "three", "four", "five")
    println(strings.windowed(size = 3, step = 2))

    println("-------------use zipWithNext------------")
    println(strings.zipWithNext())
    println(strings.zipWithNext { a: String, b: String ->
        a.length to b.length
    })
}
