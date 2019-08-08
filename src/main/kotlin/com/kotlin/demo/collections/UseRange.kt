package com.kotlin.demo.collections

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/8/8 11:24
 * Description:
 */

/**
 * 区间与数列
 * ".." 操作符定义了一个区间。
 *
 * 通常，rangeTo 被 "in" 或者 "!in" 函数实现
 *
 * 说明：".." 与 "downTo" 是闭区间 如：[1,2,3,4]
 *      而 until 是开区间. 如 [1,2,3,4)
 */
private fun useRange() {
    val element = 0
    println("isContains: ${element in 1..4}") //判断一个元素是否在区间内

    for (item in 1..4) {  //遍历区间
        println(item)
    }
    println("------------------------")

    for (item in 4 downTo 1) {  //reverse order. use "downTo" 代替 ".."
        println(item)
    }
    println("------------------------")

    //step
    for (item in 1..4 step 2) {
        println(item)
    }
    println("------------------------")

    for (item in 4 downTo 1 step 2) {
        println(item)
    }

    println("------------------------")

    // [1, 10)
    for (i in 1 until 10) {       // i in [1, 10), 10 is excluded
        println(i)
    }


    //自定义区间
    val versionRange = Version(1)..Version(40)
    println(Version(0) in versionRange)
    println(Version(1) in versionRange)

    for (item in versionRange) {
        println(item)
    }

    println(versionRange.filter { version ->
        version.number % 2 == 0
    })
}

data class Version(val number: Int) : Comparable<Version> {

    operator fun rangeTo(version: Version) = VersionRange(this, version)

    override fun compareTo(other: Version): Int = this.number - other.number

    operator fun plus(number: Int): Version = Version(this.number + number)
}

class VersionRange(private val first: Version, private val second: Version) : ClosedRange<Version>, Iterable<Version> {

    override val start: Version get() = first

    override val endInclusive: Version get() = second

    override operator fun iterator(): Iterator<Version> =
        VersionIterator(first = first, last = second, step = 1)
}

private class VersionIterator(first: Version, last: Version, val step: Int) : Iterator<Version> {
    private val finalElement = last

    private var hasNext: Boolean = if (step > 0) first <= last else first >= last

    private var next = if (hasNext) first else finalElement

    override fun hasNext(): Boolean = hasNext

    override fun next(): Version {
        val value = next
        if (value == finalElement) {
            if (!hasNext) throw kotlin.NoSuchElementException()
            hasNext = false
        } else {
            next += step
        }
        return value
    }
}

fun main(array: Array<String>) {
    useRange()
}