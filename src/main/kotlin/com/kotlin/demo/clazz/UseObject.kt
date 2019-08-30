package com.kotlin.demo.clazz.obj

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/7/25 16:47
 * Description: 对象表达式 与 对象声明
 */

/**
 * 有时候，我们需要创建一个对某个类做了轻微改动的类的对象，而不用为之显示声明新的子类。
 *
 * 针对这种情况，在Java中使用匿名内部类处理。
 *
 * 在Kotlin中， 利用 “对象表达式” 和 对象声明
 *
 * 对象表达式：
 * window.addMouseListener(object : MouseAdapter(){  ---> 对象表达式。
 *
 *      override fun mouseClicked(e: MouseEvent){...}
 *
 *      override fun mouseEntered(e: MouseEvent){...}
 * })
 *
 */

// 对象表达式------------------------------------
open class A(x: Int) {
    open val y: Int = x
}

interface B

/**
 * 可指定多个超类型，如果超类型有一个构造函数，则必须传递适当的构造函数参数给它。
 */
val ab: A = object : A(1), B {
    override val y: Int = 15
}

fun main(array: Array<String>) {
    println(ab.y)

    useObject()
}

/**
 * 任何时候，如果我们只需要一个对象，并不需要特殊超类型，可以像如下这么写。
 */
fun useObject(){
    val adHot = object {
        var x: Int = 0
        var y: Int = 0
    }
    adHot.x = 12
    adHot.y = 23
    println(adHot.x + adHot.y)
}

/**
 * 注意点：
 *
 * 匿名对象可以用作只在本地和私有作用域中声明的类型。
 *
 * 如果你使用匿名对象作为 公有函数的返回类型 或者用 作公有属性的类型，
 * 那么该函数或属性的实际类型会是匿名对象声明的超类型，如果你没有声明任何超类型，就会是 Any。
 * 在匿名对象中添加的成员将无法访问。
 */
class C {
    //私有函数，所以其返回值类型是匿名对象类型
    private fun foo() = object {
        val x: String = "x"
    }

    //共有函数，且没有声明任何超类型，此时它的超类型是 Any
    public fun pubicFoo() = object {
        val x: String = "x"
    }

    fun bar() {
        val x1 = foo().x //OK

//        val x2 = pubicFoo().x  //error, 因为返回的类型是Any,因此无法访问x
    }
}

// 对象声明 ------------------------------------
/**
 * object 名称{}   --> 声明一个 对象声明，就像变量声明一样；
 *
 * 对象声明不是一个表达式，因此不能不用在赋值语句的右边
 *
 * 这些对象可以有超类型
 * object DefaultListener: MouseAdapter{
 *
 *      override fun mouseClicked(e: MouseEvent){...}
 *
 *      override fun mouseEntered(e: MouseEvent){...}
 * }
 *
 * 注意：对象声明不能在局部作用域（即直接嵌套在函数内部），但是他们可以嵌套到其它对象声明或非内部类中。
 *
 */

class DataProvider

object DataProviderManager {

    val allDataProviders: MutableList<DataProvider> = mutableListOf()

    fun registerDataProvider(provider: DataProvider) {
        allDataProviders.add(provider)
    }
}

fun useDataProviderManager() {

    val dataProvider = DataProvider()
    DataProviderManager.registerDataProvider(dataProvider)
}

// 伴生对象 ------------------------------------
/**
 * companion object 名称  --> 伴生对象
 */