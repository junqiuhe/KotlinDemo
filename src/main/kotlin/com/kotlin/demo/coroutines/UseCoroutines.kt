package com.kotlin.demo.coroutines

import com.kotlin.demo.utils.log
import kotlinx.coroutines.*
import kotlin.coroutines.ContinuationInterceptor

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/8/9 15:40
 * Description:
 */

/**
 * 启动协程需要三样东西，“上下文”、“启动模式”、“协程体”
 *
 * 启动模式：
 * 1、DEFAULT
 *      DEFAULT：是饿汉式启动，launch调用后，会立即进入待调度状态。一旦调度器OK就开始执行
 * 2、LAZY
 * 3、ATOMIC
 * 4、UNDISPATCHED
 */

/**
 * DEFAULT：饿汉式启动，launch调用后，会立即进入待调度状态，一旦调用器OK就开始执行
 */
suspend fun useDefaultMode() {
    log(1)
    val job = GlobalScope.launch {
        //        delay(1000)
        log(2)
    }
    log(3)
    job.join()
    log(4)
}

/**
 * Lazy: 懒汉式启动，launch后并不会有任何调度行为，协程体也自然不会进入执行状态，直到我们需要它执行的时候。
 *
 * job.start.主动触发协程的调度执行
 * job.join. 隐式的触发协程的调度执行
 *
 */
suspend fun useLazyMode() {
    log(1)
    val job = GlobalScope.launch(start = CoroutineStart.LAZY) {
        log(2)
    }
    log(3)
    job.join()
    log(4)
}

/**
 * ATOMIC模式的协程在启动时无视cancel状态。
 * 实际上在遇到第一个挂起点之前，它的执行时不会停止的，而delay是一个挂起函数，因此3不会被打印。
 */
suspend fun useAtoMic() {
    log(1)

    val job = GlobalScope.launch(
        start = CoroutineStart.ATOMIC
    ) {
        log(2)
        delay(1000)
        log(3)
    }
    job.cancel()
    log(4)
    job.join()
}

fun compareDefault() = runBlocking {
    println("compare default mode")
    log(1)
    val job = GlobalScope.launch(start = CoroutineStart.DEFAULT) {
        log(2)
    }
    job.cancel()
    log(4)
    job.join()
}

/**
 * 协程在这种模式下会直接开始在当前线程下执行，直到第一个挂起点。
 * 听起来有点像 “ATOMIC”，不同之处在于 “UNDISPATCHED”不经过任何调度器即开始执行协程体.
 */
suspend fun useUndispatched() {
    log(1)
    val job = GlobalScope.launch(start = CoroutineStart.UNDISPATCHED) {
        log(2)
        delay(100)
            log(3)
    }
    log(4)
    job.join()
    log(5)
}

/**
 *  println result:
11:29:03:745 [main] 1
11:29:03:790 [main] 2
11:29:03:797 [main] 4
11:29:03:905 [DefaultDispatcher-worker-1] 3
11:29:03:905 [DefaultDispatcher-worker-1] 5
 */

suspend fun main() {

        useDefaultMode()

//    useLazyMode()

//    useAtoMic()

//    compareDefault()

//    useUndispatched()


//    val job = GlobalScope.launch {
//        delay(1000)
//        println("world!")
//    }
//
//    println("hello")

//    Thread.sleep(2000)
//    runBlocking {
//        delay(2000)
//    }

//    job.join()

//    useRunBlock()

//    useCoroutineScope()

//    useGlobalScope()

//    cancelTask()

//    cancelTask1()

//    cancelTask2()
}

fun useRunBlock() = runBlocking {
    launch {
        delay(1000L)
        println("world")
    }
    println("hello")
}

fun useCoroutineScope() = runBlocking {
    launch {
        delay(200)
        println("Task from runBlocking")
    }

    coroutineScope {
        // 创建一个协程作用域
        launch {
            delay(500L)
            println("Task from nested launch")
        }

        delay(100L)
        println("Task from coroutine scope") // 这一行会在内嵌 launch 之前输出
    }

    println("Coroutine scope is over") // 这一行在内嵌 launch 执行完毕后才输出
}

fun useGlobalScope() = runBlocking {

    GlobalScope.launch {
        repeat(1000) {
            println("I'm sleeping $it ...")
            delay(500L)
        }
    }

    delay(1300L)
}

/**
 * 协程的取消事协作的。一段协程代码必须协作才能被取消。
 * 所有kotlinx.coroutines中的挂起函数都是可被取消的。
 * 它们检查协程的取消，并在取消事抛出CancellationException，
 * 然而，如果协程正在执行计算任务，并且没有检查取消的话，那么它是不能被取消的。
 */
fun cancelTask() = runBlocking {
    val job = launch {
        repeat(1000) {
            println("job: I'm sleeping $it ...")
            delay(500L)
        }
    }

    delay(1300L) // 延迟一段时间
    println("main: I'm tired of waiting!")

    job.cancelAndJoin()

    println("main: Now I can quit.")
}

/**
 * 运行代码，并且我们可以看到调用取消后，作业仍然循环迭代并运行到它结束为止。
 */
fun cancelTask1() = runBlocking {
    val startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0
        while (i < 5) { // 一个执行计算的循环，只是为了占用 CPU
            // 每秒打印消息两次
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("job: I'm sleeping ${i++} ...")
                nextPrintTime += 500L
            }
        }
    }
    delay(100L) // 等待一段时间
    println("main: I'm tired of waiting!")

    job.cancelAndJoin() // 取消一个作业并且等待它结束

    println("main: Now I can quit.")
}

fun cancelTask2() = runBlocking {
    val startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0
        while (isActive) { // 一个执行计算的循环，只是为了占用 CPU
            // 每秒打印消息两次
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("job: I'm sleeping ${i++} ...")
                nextPrintTime += 500L
            }
        }
    }
    delay(100L) // 等待一段时间
    println("main: I'm tired of waiting!")

    job.cancelAndJoin() // 取消一个作业并且等待它结束

    println("main: Now I can quit.")
}