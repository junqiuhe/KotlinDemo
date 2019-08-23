package com.kotlin.demo.crack.coroutines

import com.kotlin.demo.utils.log
import kotlinx.coroutines.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/8/13 16:49
 * Description:
 */

data class User(val name: String, val age: Int)


//获取用户信息
fun getUser(callback: (User) -> Unit) {
    callback(User("Jackson", 20))
}

suspend fun getUserCoroutineVersion1() = suspendCoroutine<User> { continuation ->
    getUser {
        continuation.resume(it)
    }
}

suspend fun setOnclickListener() {
    val job = GlobalScope.launch(Dispatchers.Default) {
        val user = getUserCoroutineVersion1()
        log(user)
    }
    job.join()
}

/**
 * ------------异常处理版本。------------
 */
fun getUserByCallback(callback: Callback<User>) {
    try {
        val i = 1 / 0
        callback.onSuccess(User("zhangsan", 19))

    } catch (e: Throwable) {
        callback.onFailure(e)
    }
}

interface Callback<T> {

    fun onSuccess(value: T)

    fun onFailure(e: Throwable)
}

suspend fun getUserCoroutineVersion2() = suspendCoroutine<User> { continuation ->
    getUserByCallback(object : Callback<User> {
        override fun onSuccess(value: User) {
            continuation.resume(value)
        }

        override fun onFailure(e: Throwable) {
            continuation.resumeWithException(e)
        }
    })
}

suspend fun setOnclickListener1() {
    val job = GlobalScope.launch(Dispatchers.Default) {
        try {
            val user = getUserCoroutineVersion2()
            log(user)
        } catch (e: Throwable) {
            log(e.message)
        }
    }
    job.join()
}

/**
 * ------------异常处理版本。------------
 */

/**
 * 注意：仅有 launch 启动的协程出现未捕获的异常时才会触发 CoroutineExceptionHandler
 * 通过 async 启动的协程出现未捕获的异常时不会触发。
 */
suspend fun handleGlobalException() {
    log(1)
    GlobalScope.launch {
        throw ArithmeticException("Hey!")
    }.join()
    log(2)
}

/**
 * 异常传播
 * 涉及协程作用域的概念。
 * GlobeScope.
 *
 * coroutineScope.
 *      继承外部Job的上下文创建的作用域，在其内部的取消操作时双向传播的，
 *      子协程未捕获的异常也会向上传递给父协程。
 *
 * supervisorScope.
 *      同样继承外部作用域的上下文，但其内部的取消操作时单向传播的，父协程向子协程传播。
 *      意味着子协程出了异常不会影响父协程以及其它兄弟协程。
 */
suspend fun main() {
//    setOnclickListener()
//    setOnclickListener1()
//
//    handleGlobalException()

    handlerException()
}

suspend fun handlerException() {
    log(1)
    try {
        coroutineScope { //①
            log(2)

            launch {// ②
                log(3)
                launch { // ③

                    log(4)
                    delay(100)
                    throw ArithmeticException("Hey!!")
                }
                log(5)
            }

            log(6)

            val job = launch { // ④
                log(7)
                delay(1000)
            }

            try {
                log(8)
                job.join()
                log("9")
            } catch (e: Exception) {
                log("10. $e")
            }
        }

        log(11)

    } catch (e: Exception) {
        log("12. $e")
    }
    log(13)
}