package com.kotlin.demo.coroutines

import com.kotlin.demo.utils.log
import kotlinx.coroutines.Dispatchers
import java.util.concurrent.Executors
import kotlin.coroutines.*

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/8/15 14:35
 * Description:
 */

fun main() {
//    useAsyncCalcMD5()

//    useAsyncCalcMD5Version2()

//    useAsyncCalcMD5Version3()

    useAsyncCalcMD5Version4()
}

private val executor = Executors.newSingleThreadExecutor {
    Thread(it, "scheduler")
}

fun useAsyncCalcMD5() {
    log("before coroutine")
    asyncCalcMD5("test.zip") {
        log("in coroutine, Before suspend.")
        val result: String = suspendCoroutine { continuation ->
            log("in suspend block.")
            executor.submit {
                continuation.resume(calcMD5(continuation.context[FilePath]!!.path))
                log("after resume.")
            }
        }
        log("in coroutine. After suspend. result = $result")
        executor.shutdown()
    }
    log("after coroutine")
}

/**
 * 异步，通过 ContinuationInterceptor 进行拦截。进而实现线程切换。
 */
fun useAsyncCalcMD5Version2() {
    log("before coroutine")
    asyncCaclMD5Version2("test.zip") {
        log("in coroutine, Before suspend.")
        val result: String = suspendCoroutine { continuation ->
            log("in suspend block.")
            continuation.resume(calcMD5(continuation.context[FilePath]!!.path))
            log("after resume.")
        }
        log("in coroutine. After suspend. result = $result")
    }
    log("after coroutine")

    Thread.sleep(5000)
}

fun asyncCaclMD5Version2(path: String, block: suspend () -> Unit) {
    val continuation = object : Continuation<Unit> {
        override val context: CoroutineContext
            get() = FilePath(path) + Dispatchers.Default

        override fun resumeWith(result: Result<Unit>) {
            if (result.isSuccess) {
                log("resume: ${result.getOrNull()}")
            } else {
                log(result.exceptionOrNull()?.toString())
            }
        }
    }
    block.startCoroutine(continuation)
}

/**
 * 启动协程进行封装
 */
private class StandaloneCoroutine(override val context: CoroutineContext) : Continuation<Unit> {

    override fun resumeWith(result: Result<Unit>) {
        if (result.isSuccess) {
            log("resume: ${result.getOrNull()}")
        } else {
            log(result.exceptionOrNull()?.toString())
        }
    }
}

private fun launch(context: CoroutineContext, block: suspend () -> Unit) =
    block.startCoroutine(StandaloneCoroutine(context))

private fun useAsyncCalcMD5Version3() {
    log("before coroutine")
    launch(FilePath("test.zip") + Dispatchers.Default) {
        log("in coroutine, Before suspend.")
        val result: String = suspendCoroutine { continuation ->
            log("in suspend block.")
            continuation.resume(calcMD5(continuation.context[FilePath]!!.path))
            log("after resume.")
        }
        log("in coroutine. After suspend. result = $result")
    }
    log("after coroutine")
    Thread.sleep(5000)
}

/**
 * 带接收者的launch.
 */
private fun <T> launch(
    receiver: T,
    context: CoroutineContext,
    block: suspend T.() -> Unit
) = block.startCoroutine(receiver, StandaloneCoroutine(context))

private fun useAsyncCalcMD5Version4() {
    log("before coroutine")
    val coroutineContext = FilePath("test.zip") + Dispatchers.Default
    launch(coroutineContext, coroutineContext) {
        log("in coroutine, Before suspend.")
        val result: String = suspendCoroutine { continuation ->
            log("in suspend block.")
            continuation.resume(calcMD5(this@launch[FilePath]!!.path))
            log("after resume.")
        }
        log("in coroutine. After suspend. result = $result")
    }
    log("after coroutine")
    Thread.sleep(5000)
}