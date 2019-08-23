package com.kotlin.demo.coroutines

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/8/16 9:45
 * Description:
 */

fun main() = runBlocking<Unit> {
    val result = async {
        "hello world"
    }
    println(result.await())
}
