package com.kotlin.demo.collections

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/8/8 15:57
 * Description:
 */

/**
 * 集合操作：
 *  成员函数：isEmpty、get、add等等函数
 *  拓展函数：包括search、sorting、filter、变换等等
 *
 *  公共操作：
 *      1、变换
 *      2、过滤
 *      3、加减操作
 *      4、分组
 *      5、获取部分元素
 *      6、获取单个元素
 *      7、排序
 *      8、聚合操作
 */

/**
 * 这些转换函数根据提供的转换规则从现有集合构建新的集合
 */
private fun useTransform() {
    /**
     * 1、映射
     * map / mapIndexed
     * mapNotNull / mapIndexedNotNull
     * mapKeys / mapValues
     */
    val numbers = setOf(1, 2, 3)
    println(numbers.map { it * 3 })
    println(numbers.mapIndexed { index, value -> value * index })

    println(numbers.mapNotNull { if (it == 2) null else it * 3 })
    println(numbers.mapIndexedNotNull { idx, value -> if (idx == 0) null else value * idx })

    val numbersMap = mapOf("key1" to 1, "key2" to 2, "key3" to 3, "key11" to 11)
    println(numbersMap.mapKeys { it.key.toUpperCase() })
    println(numbersMap.mapValues { it.value + it.key.length })

    /**
     * 2、合并 zip
     * 合并会从两个集合中一样的位置取出值组成一个 Pair.
     * 若两个集合的长度不一致，以较小集合的size为准
     */
    val colors = listOf("red", "brown", "grey")
    val animals = listOf("fox", "bear", "wolf")

    println(colors.zip(animals))

    val twoAnimals = listOf("fox", "bear")
    println(colors.zip(twoAnimals))

    println(colors.zip(animals) { color, animal -> "The ${animal.capitalize()} is $color" })


    val numberPairs = listOf("one" to 1, "two" to 2)
//    println(numberPairs.unzip())

    val (key, value) = numberPairs.unzip()
    for (item in key) {
        println(item)
    }

    for (item in value) {
        println(item)
    }

    /**
     * 3、关联
     *
     * associateWith() 创建一个map,该map以原始结合的元素作为key, 转换结果作为value
     *
     * associateWith() 创建一个map,不过可以定制key 以及 value,通过lambda表达式
     *
     * associate() 创建一个map
     */
    val stringList = listOf("one", "two", "three", "four")
    println(stringList.associateWith { it.length })

    /**
     * 原集合中的元素的第一个字符的大写字母作为Map的key
     */
    println(stringList.associateBy { it.first().toUpperCase() }) //定制key
    /**
     * 原集合中的元素的第一个字符的大写字母作为Map的key，字符长度作为value
     */
    println(stringList.associateBy({ keySelector ->
        keySelector.first().toUpperCase()
    }, { value ->
        value.length
    }))

    val names = listOf("Alice Adams", "Brian Brown", "Clara Campbell")
    println(names.associate { element ->
        element.split(delimiters = *arrayOf(" ")).let { index ->
            index[0] to index[1]
        }
    })

    /**
     * 4、平铺
     * 如果你使用嵌套的集合，可以使用平铺操作符。
     *  flatten：嵌套的集合，经过flatten操作以后返回一个集合
     *  flatMap：提供了一种灵活的方式处理嵌套集合。它提供了一个函数映射一个集合元素到另一个集合。
     */
    val numberSets = listOf(setOf(1, 2, 3, listOf(4, 5, 6), listOf(7, 8, 9), 10))
//    println(numberSets.flatten())
    val flattenList = numberSets.flatten()
    for (item in flattenList) {
        print("$item ")
    }
    println()

    println(numberSets.flatMap { it })


    /**
     * 字符串表示
     * joinToString
     * joinTo
     */
    val transform: ((String) -> CharSequence)? = {
        when (it) {
            "one" -> "1"
            "two" -> "2"
            "three" -> "3"
            "four" -> "4"
            else -> "unknow"
        }
    }

    val result = stringList.joinToString(prefix = "{", postfix = "}", separator = ".", transform = transform)
    println(result)

    val listString = StringBuilder("The list of numbers:")
    stringList.joinTo(listString, prefix = "[", postfix = "]", transform = ::transform)
    println(listString)
}

private fun transform(input: String): CharSequence {
    return when (input) {
        "one" -> "1"
        "two" -> "2"
        "three" -> "3"
        "four" -> "4"
        else -> "unknow"
    }
}

fun main(array: Array<String>) {
    useTransform()
}