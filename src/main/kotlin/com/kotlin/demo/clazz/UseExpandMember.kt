package com.kotlin.demo.clazz.expand.member

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/7/25 10:43
 * Description:
 */

/**
 * 扩展声明为成员：在一个类内部可以为另一个类声明为扩展。
 */
class D{
    fun bar(){
    }
}

class C{
    fun baz(){
    }

    fun D.foo(){
        bar() //调用D.bar
        baz() //调用C.baz
    }

    fun caller(d: D){
        d.foo() //调用D的扩展函数
    }
}

/**
 * 分发接受者与扩展接受者的成员名字冲突时，扩展接受者优先。
 * 若要引用分发接受的成员你可以使用 “限定的this语法”
 */
class D1

class C1{
    fun D1.foo(){
        toString() //调用的是 D1.toString()
        this@C1.toString()
    }
}

fun main(array: Array<String>){
    /**
     * 因为 caller(a: A) 中调用的是扩展函数，由于扩展函数是静态的，所以在方法中调用的始终是 A 的扩展函数 A.foo
     * 因为 B1, B存在继承关系，所以这里会调用对应类中的扩展函数
     */
    B().calller(A())  //A.foo in B
    B().calller(A1()) //A.foo in B

    B1().calller(A()) //A.foo in B1
    B1().calller(A1())  //A.foo in B1
}

open class A

class A1: A()

open class B{
    open fun A.foo() {
        println("A.foo in B")
    }

    open fun A1.foo(){
        println("A1.foo in B")
    }

    fun calller(a: A){
        /**
         * 由于扩展函数是静态的，所以此处调用仍然是 接受者为A 的扩展函数
         */
        a.foo()
    }
}

class B1: B(){
    override fun A.foo() {
        println("A.foo in B1")
    }

    override fun A1.foo() {
        println("A1.foo in B1")
    }
}