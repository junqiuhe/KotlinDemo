package com.kotlin.demo.collections.order

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/8/9 10:15
 * Description:
 */

class Version(val major: Int, val minor: Int) : Comparable<Version> {

    override fun compareTo(other: Version): Int {
        return when {
            this.major != other.major -> this.major - other.major
            this.minor != other.minor -> this.minor - other.minor
            else -> 0
        }
    }
}

fun main(array: Array<String>) {
    println(Version(1, 2) > Version(1, 3))
    println(Version(2, 0) > Version(1, 5))

    val lengthComparator = Comparator<String> { o1: String, o2: String ->
        o1.length - o2.length
    }
    println(listOf("aaa", "bb", "c").sortedWith(lengthComparator))
    println(listOf("aaa", "bb", "c").sortedWith(compareBy { it.length }))

    println(listOf("aaa", "bb", "c").sortedBy { it.length })
}