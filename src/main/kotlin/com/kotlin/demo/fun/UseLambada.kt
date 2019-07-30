package com.kotlin.demo.`fun`

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/7/26 10:28
 * Description: 高阶函数 和 lambada表达式
 */

/**
 * Kotlin中，函数是 “头等的”，意味着它们可以存储在变量与数据结构中，
 * 可以作为参数传递给其它 “高阶函数” 以及 “从其它高阶函数” 返回。
 *
 * Kotlin使用一系列 “函数类型” 来表示函数并提供一组特定的语言结构，如 “lambada表达式”
 *
 * 高阶函数：将函数作为参数 或 返回值的函数。
 */

/**
 * 参数 combine 具有 “函数类型” (R, T) -> R，因此 fold 接受一个函数作为参数，
 * 该函数接受类型分别为 R 与 T 两个参数并返回一个 R 类型的值
 */
fun <T, R> Collection<T>.fold(
    initializer: R,
    combine: (acc: R, element: T) -> R
): R {

    var accumulator: R = initializer
    for (element: T in this) {
        accumulator = combine(accumulator, element)
    }
    return accumulator
}

/**
 * 为了调用 fold，需要传给它一个 “函数类型的实例” 作为参数，在高阶函数调用处，lambada表达式广泛用于此目的。
 *
 * lambada表达式在高阶函数的目的：声明一个函数类型的实例
 */
fun useFold() {
    val items = listOf(1, 2, 3, 4, 5)

    val result = items.fold(0) { acc: Int, element: Int ->
        val result = acc + element
        result
    }
    println("result: $result")

    val joinedToString = items.fold("") { acc: String, element: Int ->
        acc + element
    }
    println(joinedToString)

    /**
     * 函数引用也可用于高阶函数调用
     */
    println(items.fold(1, ::time))


}

/**
 * 定义一个普通函数
 * ::time  --> 函数引用
 */
fun time(a: Int, b: Int): Int {
    return a * b
}

/**
 * 函数类型：
 *
 * Kotlin 使用类似 (Int) -> String 的一系列函数类型来处理函数声明：
 *      val onClick: () -> Unit = ...
 *
 * 这些类型具有与函数签名相对应的特殊表示法，即它们的参数和返回值。
 * 说明：
 *      1、所有函数类型都有一个圆括号括起来的参数累些列表以及一个返回类型：(A, B) -> C。
 *        表示接受类型分别为 A 与 B两个参数并返回一个C类型值的函数类型。
 *        参数类型列表可以为空，如 () -> A。返回值不可省略，若没有返回值，用 Unit 表示
 *
 *      2、函数类型可以有一个额外的接受者类型，它在表示法中的点之前指定：
 *        类型 A.(B) -> C 表示可以在 接受者对象 A 以一个B类型参数来调用并返回一个 C 类型值的函数
 *
 *      3、挂起函数 属于特殊种类的函数类型，它的表示法中有一个 suspend 修饰符，例如 suspend () -> Unit 或者 suspend A.(B) -> C
 *
 */
fun declareFunType() {

    //val funType: (x: Int, y: Int) -> Unit    //就是一个生命了一个函数类型。
    val funType: (Int, Int) -> Unit = { x: Int, y: Int ->
        println("$x + $y = ${x + y}")
    }

    fun innerFuc(num1: Int, num2: Int, funType: (Int, Int) -> Unit) {
        funType(num1, num2)
    }
    innerFuc(1, 2, funType)

    //------------------------------------------
    /**
     * 声明了一个可null的函数类型
     */
    val a: ((Int, Int) -> Int)?

    /**
     * val b: (Int) -> ((Int) -> Unit) 看成 (A) -> ((B) -> C)
     *
     * 接受一个输入参数 A并返回一个 函数类型 (B) -> C
     */
    val b: (Int) -> ((Int) -> Unit) = { i: Int ->

        println(i)

        val c: (Int) -> Unit = { it ->
            print(it)
        }
        c

    }
    useB(1234, b)

    /**
     * c: ((Int) -> (Int)) -> Unit 看成 ((A) -> (B)) -> Unit
     *
     * 接受一个输入参数为 函数类型：(A) -> (B) 并 返回值为 Unit.
     */
    val c: ((Int) -> (Int)) -> Unit

}

fun useB(num: Int, b: (Int) -> ((Int) -> Unit)) {
    val c: (Int) -> Unit = b(num)
    c(num)
}


//---------------------------------------
/**
 * 函数类型实例化
 *
 * 以下几种方式可以实例化函数类型
 *  1、使用函数字面值的代码块
 *      1.1、lambda表达式：{a, b -> a + b}
 *      1.2、匿名函数: fun(s: String): Int{
 *                      return s.toIntOrNull?:0
 *                  }
 *
 *  2、使用已有声明的可调用引用
 *      2.1、顶层、局部、成员、拓展函数： ::isOdd、String::toInt
 *      2.2、顶层、成员、扩展属性: List<Int>::size
 *      2.3、构造函数： ::Regex
 *
 *  3、使用实现 函数类型接口的自定义类的实例：
 */
class IntTransformer : (Int) -> String {
    override fun invoke(p1: Int): String {
        return "IntTransformer: $p1"
    }
}

/**
 * 函数类型实例调用
 *
 * 函数类型的值可以通过其 "invoke(...)操作符" 调用: f.invoke(x) 或者直接 f(x)。
 */
fun invoke() {
    val stringPlus: String.(String) -> String = String::plus
    val intPlus: (Int, Int) -> Int = Int::plus

    println(stringPlus.invoke("<-", "->"))
    println(stringPlus("hello ", "world"))
    println("hello".stringPlus("world"))

    println(intPlus.invoke(1, 1))
    println(intPlus(1, 2))
}

/**
 * 带有接收者的函数字面值
 *
 * 带有接收者的函数类型，如 A.(B) -> C
 *
 * 这样的函数字面值内部，传给调用的接收者对象成为隐式的 this，以便访问接收者对象的成员而无需额外的限定符
 */

val sum: Int.(Int) -> Int = { other -> plus(other) }

class HTML {
    fun body(params1: String, params2: String) {
        println("HTML.body $params1, $params2")
    }
}

/**
 * (A, B) -> C
 * 带有接收者的函数字面量 A.(B) -> C
 *
 */
fun html(params1: String, params2: String, method: HTML.(String, String) -> Unit): HTML {
    val html = HTML()

    html.method(params1, params2)

    return html
}

fun useHtml() {
    html("hello", "world") { s1: String, s2: String ->
        body(s1, s2)
    }
}

fun main(array: Array<String>) {
    useFold()
    declareFunType()

    val intFunction: (Int) -> String = IntTransformer()
    println(intFunction(123))

    invoke()

    1.sum(2)

    useHtml()
}