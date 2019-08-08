package com.kotlin.demo.collections

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/8/8 17:29
 * Description:
 */

/**
 * 过滤操作时集合处理中最受欢迎的任务之一。
 *
 * 过滤条件接收 一个集合元素 一个 boolean 返回值：
 *      true: 表示给定的元素是相匹配的。
 *      false：表示不匹配。
 *
 *      1、按谓词过滤 filter.
 */

private fun useFilter() {
    /**
     * 一、按谓词过滤
     * 1、filter
     * 2、filterIndexed. 如果你想在filter中使用元素下标.
     * 3、filterNot. 与filter相反
     * 4、filterIsInstance
     * 5、filterNotNull.
     */
    val stringList = listOf("one", "two", "three", "four")
    println(stringList.filter { it.length > 3 })

    println(stringList.filterIndexed { index: Int, s: String ->
        (index != 0) && (s.length < 5)
    })

    println(stringList.filterNot {
        it.length <= 3
    })

    val numbers = listOf(null, 1, "two", 3.0, "four")
    println("All String elements in upper case:")
    numbers.filterIsInstance<String>().forEach {
        println(it.toUpperCase())
    }

    numbers.filterNotNull().forEach {
        println(it)
    }

    val numbersMap = mapOf("key1" to 1, "key2" to 2, "key3" to 3, "key11" to 11)
    val filteredMap = numbersMap.filter { (key, value) ->
        key.endsWith("1") && value > 10
    }
    println(filteredMap)

    //-----------------------------------------
    /**
     * 划分
     * partition -> 通过谓词筛选集合，并将不匹配的元素保存在单独的列表中
     * 第一个列表包含匹配谓词的元素，第二个列表包含原始集合中的所有其他元素
     */
    val (first, second) = stringList.partition { it.length > 3 }
    println(first)
    println(second)

    //-----------------------------------------
    /**
     * 检验谓词
     * 1、any: 如果至少有一个元素与给定谓词匹配，则返回true。
     * 2、none: 如果所有元素都不匹配给定谓词，则返回true。
     * 3、all: 如果所有元素都匹配给定谓词，则返回true。
     *    注意，当使用空集合上的任何有效谓词调用all,none()时，将返回true。调用any时，返回false.
     */
    println(stringList.any { it.endsWith(suffix = "e") })
    println(stringList.none { it.endsWith("a") })
    println(stringList.all { it.endsWith(suffix = "e") })

    println(emptyList<Int>().any { it > 5 })
    println(emptyList<Int>().none { it > 5 })
    println(emptyList<Int>().all { it > 5 })
}


fun main(array: Array<String>) {
    useFilter()
}