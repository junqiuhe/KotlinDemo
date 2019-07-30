package com.kotlin.demo.data.clazz

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/7/25 11:42
 * Description:
 */

/**
 *
 * 数据类：利用 data 关键字标记的类
 *
 * 编辑器自动从主构造函数中声明的所有属性导出以下成员：
 * 1、equals() / hashCode 对；
 * 2、toString() 格式是 "User(name=John, age=42)"
 * 3、componentN()函数 按声明顺序对应于所有属性；
 * 4、copy() 函数
 *
 * 为了保证生成代码的一致性以及有意义的行为，数据类必须满足以下要求：
 * 1、主构造函数需要至少一个参数；
 * 2、主构造函数的所有参数需要标记为 val 或 var；
 * 3、数据类不能是抽象、开放、密封或者内部的；
 *
 * 注意点：对于那些自动生成的函数，编译器只能使用在主构造函数内部定义的属性。
 *
 * 如需在生成的实现中排除一个属性，请将其声明在类体中：
 */
data class User(val name: String, val age: Int)

//toString()、equals()、hashCode()以及copy()的实现只会用到 name 属性.
data class Person(val name: String){
    val age: Int = 0
}

fun main(array: Array<String>) {
    val user = User(name = "Jackson", age = 1)
    println(user)

    val oldJack = user.copy(age = 35)
    println(oldJack)

    /**
     * 数据类与解构声明
     */
    val (name, age) = oldJack
    println("$name, $age years of age")
}

