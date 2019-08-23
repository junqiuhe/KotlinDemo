package com.kotlin.demo.crack.coroutines

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/8/13 17:20
 * Description:
 */

/**
 * 自定义全局的异常处理器.
 */
class GlobalCoroutineExceptionHandler: CoroutineExceptionHandler{

    override val key: CoroutineContext.Key<*> = CoroutineExceptionHandler

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        println("Coroutine exception: $exception")
    }
}