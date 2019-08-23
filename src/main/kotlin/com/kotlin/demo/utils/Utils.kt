package com.kotlin.demo.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/8/15 14:29
 * Description:
 */

val dateFormat = SimpleDateFormat("HH:mm:ss:SSS")

val now = {
    dateFormat.format(Date(System.currentTimeMillis()))
}

fun log(msg: Any?) {
    println("${now()} [${Thread.currentThread().name}] $msg")
}