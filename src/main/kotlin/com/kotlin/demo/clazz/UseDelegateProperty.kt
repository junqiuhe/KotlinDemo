package com.kotlin.demo.clazz

import kotlin.properties.Delegates
import kotlin.reflect.KProperty

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/7/24 15:27
 * Description:
 */

/**
 * 1、延迟属性(lazy properties): 其值只在首次访问时计算
 * 2、可观察属性(observable properties): 监听器会收到有关此属性变更的通知
 * 3、把多个属性存储在一个映射(map)中，而不是每个存在单独的字段中。
 *
 * 为了覆盖这些（以及其它）情况，Kotlin支持 “委托属性”
 * 语法：
 * 	val/var <属性名>:<类型> by <表达式>,
 *provideDelegate
 * 说明：by 后面的表达式是该委托，因为属性对应的 get()/set()会被委托给它的 getValue() / setValue() 方法
 *
 * 局部委托属性 (自1.1 起)
 * 从version 1.1开始，可以为局部属性声明为委托属性。例如惰性初始化，
 * fun example(computeFoo: () -> Foo){
 *      val memoizedFoo by lazy(computeFoo)
 *      ...
 * }
 *
 * 提供委托 (provideDelegate) 自 version1.1 开始
 */
fun main(args: Array<String>){

    val example = Example()
    example.p = "hello world"

    //延迟初始化
    val lazyProperty: String by lazy{
        "Jackson"
    }
    println(lazyProperty)

    //自定义延迟初始化器
    val customLazyProperty: String by customLazy {
        "hello custom lazy property"
    }
    val customLazyProperty1: String by CustomLazyImpl {
        "hello world......."
    }
    println(customLazyProperty)
    println(customLazyProperty1)

    val user = User()
    user.name = "first"
    user.name = "second"
}

//---------------委托属性的定义----------------
class Example{
    var p: String by Delegate()
}
class Delegate{

    operator fun getValue(thisRef: Any?, property: KProperty<*>): String{
        return "$thisRef, thank you for delegating '${property.name}'"
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("$value has been assigned to '${property.name}' in $thisRef.")
    }
}

//---------------自定义延迟初始化器----------------
interface CustomLazy<out T>{
    val value: T
}

class CustomLazyImpl<out T>(initializer: () -> T): CustomLazy<T> {

    private val initializer: (() -> T)? = initializer

    private var _value: Any? = null

    override val value: T
        get() {
            val v1 = _value
            if(v1 != null){
                return _value as T
            }

            val typeValue = initializer!!()
            _value = typeValue
            return _value as T
        }
}

operator fun <T> CustomLazy<T>.getValue(thisRef: Any?, property: KProperty<*>): T = value

fun <T> customLazy(initializer: () -> T) = CustomLazyImpl(initializer)

//---------------可观察属性 Observable----------------
class User{
    var name: String by Delegates.observable("<no name>"){
        property, oldValue, newValue -> println("$oldValue -> $newValue")
    }
}

//---------------把属性存储在映射中----------------
//在库函数中为 Map 实现拓展函数 getValue 方法
class Student(map: Map<String, Any?>){
    val name: String by map
    val age: Int by map
}

fun useStudent(){
    val student = Student(
        mapOf(
            "name" to "John Doe",
            "age" to 25
        )
    )

    println(student.name)
    println(student.age)
}