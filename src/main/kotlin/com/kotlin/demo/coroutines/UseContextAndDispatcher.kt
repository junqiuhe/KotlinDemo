package com.kotlin.demo.coroutines

import kotlinx.coroutines.*

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/8/13 9:16
 * Description:
 */

private fun log(msg: String) {
    println("$msg : I'm working in thread ${Thread.currentThread().name}")
}

fun useDispatcher() = runBlocking {

    launch {
        log("main RunBocking")
    }

    //不受限的-将工作在主线程
    launch(Dispatchers.Unconfined) {
        log("Unconfined")
    }

    launch(Dispatchers.Default) {
        log("default")
    }

    launch(newSingleThreadContext("MyOwnThread")) {
        log("newSingleThreadContext")
    }
}

/**
 * 子协程，当一个父协程被取消的时候，所有它的子协程也会被递归的取消。
 */
fun useChildCoroutine() = runBlocking {

    //孵化了两个子作业，其中一个是通过GlobalScope启动。
    val request = launch {

        GlobalScope.launch {
            println("job1: I run in GlobalScope and execute independently!")
            delay(1000)
            println("job1: I am not affected by cancellation of the request")
        }

        //另一个则承袭了父协程的上下文，
        launch {
            delay(100)
            println("job2: I am a child of the request coroutine")
            delay(1000)
            println("job2: I will not execute this line if my parent request is cancelled")
        }
    }

    delay(500)
    request.cancel()
    delay(1000)
    println("main: Who has survived request cancellation?")
}


//协程作用域，CoroutineScope.
class Activity : CoroutineScope by CoroutineScope(Dispatchers.Default) {

    fun doSomething() {
        repeat(10) {
            launch {
                delay((it + 1) * 200L)
                println("Coroutine $it is done")
            }
        }
    }

    fun destroy() {
        cancel()
    }
}
private fun testActivity() = runBlocking {
    val activity = Activity()
    activity.doSomething()

    println("Launched coroutines")
    delay(500L)

    println("Destroying activity!")
    activity.destroy()

    delay(1000L)
}

fun main() {
//    useDispatcher()
//    useChildCoroutine()

    testActivity()
}

