package com.kotlin.demo.dsl

import java.time.LocalDate
import java.time.Period

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/7/31 17:35
 * Description:
 */

/**
 *
 * Kotlin DSL 的设计依赖于许多语言特性，其中包括 “带接收者的 lambda” 和 “invoke约定”
 *
 * Kotlin的DSL是完全静态类型的，就像语言的其他功能一样。
 * 静态类型的所有优势，如编译时错误的检测，以及更好的IDE支持。
 *
 * DSL能做什么?
 *
 *  val yesterday = 1.days.ago
 *
 * fun createSimpleTable() = createHTML()
 * .table{
 *      tr{
 *          td{ + "cell" }
 *      }
 * }
 *
 * 1.1、领域特定语言的概念
 *      通用编程语言 与 领域特定语言。
 *      1、领域特定语言更趋于 “声明式”，“声明式语言”描述了想要的结果并将执行细节留给解释它的引擎。
 *
 *      2、通用编程语言是 “命令式”，“命令式语言”描述了执行操作所需步骤的确切序列。
 *
 * 1.2、内部DSL、外部DSL。
 *
 *      外部DSL：有着自己独立的语法。
 *
 *      内部DSL：用通用编程语言编写的程序的一部分，使用了和通用编程语言完全一致的语法。
 *      其实就是：实现为通用编程语言（Kotlin）的库，旨在完成特定任务（构建SQL查询）的代码。
 *
 * 1.3、DSL的结构
 *      DSL与普通的API之间没有明确定义的边界。
 *
 *      DSL的特征：结构或者说是文法。
 *
 * 2、构建结构化的API ：DSL中带接收者的lambda
 *
 *  带接收者的lambda是Kotlin的一个强大特性，它可以让你使用一个结构来构建API。
 *
 * 2.1、带接收者的lambda 和 扩展函数类型
 *
 *      StringBuilder.() -> Unit //扩展函数类型的声明.
 *
 *      String.(Int, Int) -> Unit //
 */

//定义接收普通lambda参数的函数
fun appendString(builderAction: (StringBuilder) -> Unit): String {
    val sb = StringBuilder()
    builderAction(sb)
    return sb.toString()
}

//定义接收 带接收者的lambda类型参数 的函数
/**
 * 这里扥 builderAction并不是 StringBuilder类的方法，
 * 它是一个函数类型的参数，但可以用调用扩展函数一样的语法调用它。
 */
fun appendString1(builderAction: StringBuilder.() -> Unit): String {
//    val sb = StringBuilder()
//    sb.builderAction()
//    return sb.toString()
    return StringBuilder().apply(builderAction).toString()
}

fun useAppendFunc() {
    var appendString = appendString { sb ->
        sb.append("hello ")
        sb.append("world")
    }
    println(appendString)

    appendString = appendString1 {
        append("hello ")
        append("world")
    }
    println(appendString)
}

fun main(array: Array<String>) {
    useAppendFunc()

    /**
     * 可以声明一个扩展函数类型的变量。
     *      可以像扩展函数一样调用它，也可以将它作为参数传给一个期望带接收者的lambda为参数的函数
     */
    val appendExc1: StringBuilder.() -> Unit = {
        this.append("!")
    }
    val stringBuilder = StringBuilder("Hi")
    stringBuilder.appendExc1()

    println(stringBuilder)

    println(buildString(appendExc1))

    val applyString = StringBuilder().apply {
        append("Jackson say : ")
    }
    with(applyString) {
        append("hello")
    }
    println(applyString)

    createTable()


    useGreeter()

    useImportantIssues()

    useDependency()

    useShould()

    useDay()
}

//---------------------------------------------
/**
 * 在HTML构建器中使用带接收者的lambda
 *
 * fun createSimpleTable() = createHTML().
 *      table{
 *          tr{
 *              td{ "+cell" }
 *          }
 *      }
 */
open class Tag(val name: String) {
    private val children = mutableListOf<Tag>()

    protected fun <T : Tag> doInit(child: T, init: T.() -> Unit) {
        child.init()
        children.add(child)
    }

    override fun toString(): String = "<$name>${children.joinToString("")}</$name>"
}

fun table(init: TABLE.() -> Unit) = TABLE().apply(init)

class TABLE : Tag("table") {

    fun tr(init: TR.() -> Unit) = doInit(TR(), init)
}

class TR : Tag("tr") {
    fun td(init: TD.() -> Unit) = doInit(TD(), init)
}

class TD : Tag("td")

fun createTable() {
    val table = table {
        tr {
            td {

            }
        }
    }
    println(table)

    println(createAnotherTable())
}

fun createAnotherTable() = table {
    for (i in 1..2) {
        tr {
            td {
            }
        }
    }
}

//----------------------------------------------
fun createSimpleHTML() = createHTML().table {
    tr {
        td {

        }
    }
}

fun createHTML(): HTML {
    return HTML()
}

class HTML {
    fun table(init: TABLE.() -> Unit) = TABLE().apply(init)
}


/**
 * 3、invoke约定。
 *      invoke约定允许把自定义类型的对象当作函数一样调用。
 */

/**
 * 3.1、invoke约定：像函数一样可以调用的对象
 *
 * 以下：bavarianGreeter("Dmitry") 被翻译成了 bavarianGreeter.invoke("Dmitry")
 * invoke约定：提供了一种方法，用更简洁、清晰的表达式替换了冗长的表达式。
 */
class Greeter(val greeting: String) {
    operator fun invoke(name: String) {
        println("$greeting, $name")
    }
}

fun useGreeter() {
    val bavarianGreeter = Greeter("Servus")
    bavarianGreeter("Dmitry")
}

/**
 * 3.2、invoke约定和函数式类型
 *
 * lambda除非是内联的，都被翻译成了实现了函数式接口(Function1等)的类。如下：
 */
interface Function2<in P1, in P2, out R> {
    operator fun invoke(p1: P1, p2: P2): R
}

data class Issue(
    val id: String, val project: String, val type: String,
    val priority: String, val description: String
)

/**
 * 实现函数类型接口的自定义类
 */
class ImportantIssuesPredicate(val project: String) : (Issue) -> Boolean {

    override fun invoke(issue: Issue): Boolean {
        return issue.project == project && issue.isImportant()
    }

    private fun Issue.isImportant(): Boolean {
        return type == "Bug" && (priority == "Major" || priority == "Critical")
    }
}

fun useImportantIssues() {
    val i1 = Issue("IDEA-154446", "IDEA", "Bug", "Major", "Save settings failed")
    val i2 = Issue("KT-12183", "Koltin", "Feture", "Normal", "ddddddddd")

    val predicate = ImportantIssuesPredicate("IDEA")

    for (issue in listOf(i1, i2).filter(predicate)) {
        println(issue.id)
    }
}

/**
 * DSL中的 “invoke” 约定：在Gradle中声明依赖.
 */
class DependencyHandler {

    fun compile(coordinate: String) {
        println("Added dependency on $coordinate")
    }

    operator fun invoke(body: DependencyHandler.() -> Unit) {
        body()
    }
}

fun useDependency() {

    val dependencies = DependencyHandler()

    dependencies.compile("org.jetbrains.kotlin:kotlin-stdlib:1.0.0")

    /**
     * 被翻译成了该语句.
     * dependencies.invoke({
     *      this.compile(""org.jetbrains.kotlin:kotlin-reflect:1.0.0")
     * })
     */
    dependencies {
        compile("org.jetbrains.kotlin:kotlin-reflect:1.0.0")
    }
}

/**
 * 4、实践中的 Kotlin DSL
 */

/**
 * 4.1、把中缀调用链接起来：测试框架中的 "should"
 */

interface Matcher<T> {
    fun test(value: T)
}

class StartWith(private val prefix: String) : Matcher<String> {
    override fun test(value: String) {
        if (!value.startsWith(prefix)) {
            throw AssertionError("String $value does not start with $prefix")
        }
    }
}

//infix fun <T> T.should(matcher: Matcher<T>) = matcher.test(this)

infix fun <T> T.should(matcher: Matcher<T>) {
    matcher.test(this)
}

fun useShould() {
    val s = "kot: Jackson"
    s should StartWith("kot")

    "kotlin" should start with "kot"

    "kotlin".should(start).with("kot")
}


//-------------------------
object start

//infix fun String.should(x: start): StartWrapper = StartWrapper(this)

infix fun String.should(x: start): StartWrapper {
    return StartWrapper(this)
}

class StartWrapper(private val value: String) {
    infix fun with(prefix: String) {
        if (!value.startsWith(prefix)) {
            throw AssertionError("String $value does not start with $prefix")
        }
    }
}

/**
 * 2、在基本数据类型上定义扩展：处理日期
 */
val Int.days: Period
    get() = Period.ofDays(this)

val Period.ago: LocalDate
    get() = LocalDate.now() - this

val Period.fromNow: LocalDate
    get() = LocalDate.now() + this

fun useDay() {
    val yesterday = 1.days.ago
    val tomorrow = 1.days.fromNow

    println(yesterday)
    println(tomorrow)
}

/**
 * Kotlin为SQL设计的内部DSL --> Exposed框架
 * Anko --> 动态创建Android UI.
 */