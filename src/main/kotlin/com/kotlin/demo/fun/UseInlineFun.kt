package com.kotlin.demo.`fun`

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/7/29 10:22
 * Description:
 */

/**
 * 一、内联函数
 *
 * 使用 “高阶函数” 会带来一些运行时的效率损失：每一个函数都是一个对象，并且会捕获一个闭包。
 *      即那些在函数体内会访问到的变量。
 *      内存分配（对于函数对象和类）和虚拟调用会引入运行时间开销
 *
 * 在许多情况下通过内联话 lambda表达式 可以消除这类的开销。
 *
 * inline 修饰符影响函数本身和传给他的 lambda 表达式：所有这些都将内联到调用处。
 *
 * 内联可能导致生成的代码增加；不过如果我们使用得当（避免内联过大函数），性能上会有所提升。尤其是在循环中的 “超多态” 调用处
 *
 * 二、禁用内联：
 *
 * 如果希望只内联一部分传给内联函数的 lambda表达式函数，那么可以用 noinline 修饰符标记不希望内联的函数参数
 *
 * 注：
 * 1、可以内联的 lambda表达式只能在 “内联函数内部调用” 或者作为 “可内联的参数传递”
 * 2、noinline 修饰的 可以以任何我们喜欢的方式操作：存储在字段、传递它等等。
 *
 * 三、非局部返回
 *
 * 四、具体化的类型参数：内联函数支持具体化参数。
 *  由于函数时内联的，不需要反射，正常的操作符如 is 和 as现在都能用了。
 * 如
 * inline fun <reified T: Activity> Activity.newIntent(){
 *      val intent: Intent = Intent(this, T::class.java)
 *      startActivity(intent)
 * }
 */

inline fun foo(inlined: () -> Unit, noinline noInlined: () -> Unit) {
    // do something.

//    baz1(inlined) compile error, because 只能在 "内联函数内部调用" 或者作为 “可内联函数的参数传递”

    baz {
        println("hello, world")
        return
    }

    //----------------------
    inlined.invoke()
    noInlined()
}

fun baz1(func: () -> Unit) {
    func()
}

inline fun baz(func: () -> Unit) {
    func()
}


inline fun <reified T> membersOf() = T::class.members

fun main(array: Array<String>) {
    foo({
        println("inlined function")
    }, {
        println("noInlined function")
    })

    println(membersOf<StringBuilder>().joinToString(separator = "\n"))
}