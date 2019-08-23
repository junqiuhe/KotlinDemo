package com.kotlin.demo.coroutines

import com.kotlin.demo.utils.log
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.*

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/8/15 11:46
 * Description:
 */

/**
 * 协程的异步需要依赖比它更底层的API支持，在Kotlin中，这个所谓的底层API就就是线程了。
 */
fun main() {
    log("before coroutine")
    asyncCalcMD5("test.zip") {
        log("in coroutine, Before suspend.")
        val result: String = suspendCoroutine { continuation ->
            log("in suspend block.")
            continuation.resume(calcMD5(continuation.context[FilePath]!!.path))
            log("after resume.")
        }
        log("in coroutine. After suspend. result = $result")
    }
    log("after coroutine")
}

fun asyncCalcMD5(path: String, block: suspend () -> Unit) {

    val continuation = object : Continuation<Unit> {

        override val context: CoroutineContext
            get() = FilePath(path)

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
 * 上下文，用来存放我们需要的信息，可以灵活的自定义
 */
class FilePath(val path: String) : AbstractCoroutineContextElement(FilePath) {
    companion object Key : CoroutineContext.Key<FilePath>
}

fun calcMD5(path: String): String {
    log("calc md5 for $path.")
    //暂时用这个模拟耗时
    Thread.sleep(1000)
    //假设这就是我们计算得到的 MD5 值
    return System.currentTimeMillis().toString()
}
