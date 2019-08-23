package com.kotlin.demo.coroutines

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/8/12 17:04
 * Description:
 */

/**
 * 组合挂起函数，
 */
suspend fun doSomeThingUsefulOne(): Int {
    delay(1000L)
    return 13
}

suspend fun doSomeThingUsefulTwo(): Int {
    delay(1000L)
    return 29
}

/**
 * 按照顺序调用。
 */
fun defaultOrder() = runBlocking {
    val time = measureTimeMillis {
        val one = doSomeThingUsefulOne()
        val two = doSomeThingUsefulTwo()

        println("the answer is ${one + two}")
    }
    println("Completed in $time ms")
}

/**
 * async类似于launch。它启动了一个单独的协程，这是一个轻量级的线程并与其它所有的协程一起并发的工作。
 * 不同之处在于launch返回一个Job并且不附带任何结果值，而async返回一个Deferred ---> 一个轻量级非阻塞future。
 */
suspend fun useAsync() {
    val job = GlobalScope.launch {
        val time = measureTimeMillis {
            val one = async { doSomeThingUsefulOne() }
            val two = async { doSomeThingUsefulTwo() }

            println("the answer is ${one.await() + two.await()}")
        }
        println("Completed in $time ms")
    }
    job.join()
}

/**
 *
 */
fun useLazyAsync() = runBlocking {
    val time = measureTimeMillis {
        val one = async(start = CoroutineStart.LAZY) { doSomeThingUsefulOne() }
        val two = async(start = CoroutineStart.LAZY) { doSomeThingUsefulTwo() }

        /**
         * 若不手动启动，其行为是顺序调用
         */
        one.start()
        two.start()

        println("the answer is ${one.await() + two.await()}")
    }
    println("Completed in $time ms")
}

/**
 * somethingUsefulOneAsync、somethingUsefulTwoAsync不是挂起函数，它们可以在任何地方被使用。
 * 它们总是在调用它们的代码中意味着 “异步执行”
 *
 * kotlin的协程中 ”强烈不推荐“ 如此使用
 */
fun somethingUsefulOneAsync() = GlobalScope.async {
    doSomeThingUsefulOne()
}

fun somethingUsefulTwoAsync() = GlobalScope.async {
    doSomeThingUsefulTwo()
}

fun useSomethingUsefulOneAndTwoAsync() {
    val time = measureTimeMillis {
        val one = somethingUsefulOneAsync()
        val two = somethingUsefulTwoAsync()

        runBlocking {
            println("The answer is ${one.await() + two.await()}")
        }
    }
    println("Completed in $time ms")
}

//---------使用async的结构化并发 (Kotlin推荐的方式)-------------
suspend fun concurrentSum(): Int = coroutineScope {
    val one = async { doSomeThingUsefulOne() }
    val two = async { doSomeThingUsefulTwo() }
    one.await() + two.await()
}

suspend fun useConcurrentSum() {
    val time = measureTimeMillis {
        println("The answer is ${concurrentSum()}")
    }
    println("Completed in $time ms")
}

//--------------------------------------------------------
suspend fun failedConcurrentSum(): Int = coroutineScope {
    val one = async(start = CoroutineStart.LAZY) {
        try {
            delay(Long.MAX_VALUE)
            42
        } finally {
            println("First child was cancelled")
        }
    }

    val two = async<Int>(start = CoroutineStart.LAZY) {
        println("Second child throws an exception")
        throw ArithmeticException()
    }

    one.start()
    two.start()

    one.await() + two.await()
}

fun main() = runBlocking<Unit> {
    //    defaultOrder()

//    useAsync()
//    useLazyAsync()

//    useSomethingUsefulOneAndTwoAsync()

//    useConcurrentSum()

    try {
        failedConcurrentSum()
    } catch (e: ArithmeticException) {
        println("Computation failed with ArithmeticException")
    }
}