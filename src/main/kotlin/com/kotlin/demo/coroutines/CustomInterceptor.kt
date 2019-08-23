package com.kotlin.demo.coroutines

import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext
import com.kotlin.demo.utils.log
import kotlinx.coroutines.*

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/8/16 11:22
 * Description:
 */

class MyContinuationInterceptor : ContinuationInterceptor {

    companion object Key : CoroutineContext.Key<MyContinuationInterceptor>

    override val key = MyContinuationInterceptor

    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> =
        MyContinuation(continuation)
}

class MyContinuation<T>(val continuation: Continuation<T>) : Continuation<T> {

    override val context = continuation.context

    override fun resumeWith(result: Result<T>) {
        log("<MyContinuation> $result")
        continuation.resumeWith(result)
    }
}

suspend fun main() {
    log(0)

    GlobalScope.launch {
        log(1)
        val job = async {
            log(2)
            delay(1000)
            log(3)
            "Hello"
        }
        log(4)
        val result = job.await()
        log("5. $result")
    }.join()

    log(6)
}