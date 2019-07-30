package com.kotlin.demo.clazz.nested.inner

import com.kotlin.demo.clazz.OnClickListener
import com.kotlin.demo.clazz.OnLongClickListener

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/7/25 15:07
 * Description:
 */

/**
 * 类可以嵌套在其它类中：
 * 嵌套类 --> 相当于Java中的静态内部类，没有持有外部类的引用
 * 内部类(利用 inner 关键字标记)，能够访问外部类的成员 --> 相当于Java中的内部类，持有外部类的引用
 */
class Outer{

    private val bar: Int = 1

    class Nested{
        fun foo() = 2

        fun printlnOuterReference(){
            val outer = Outer()
            println(outer.bar)
//            println(bar)  //不能访问直接访问外部类的属性
        }
    }

    inner class Inner{
        fun foo() = bar
    }
}

fun main(array: Array<String>) {

    val a = "hello world"

    Outer.Nested().foo() //嵌套类的使用方式

    Outer().Inner().foo() //内部类的使用方式

    /**
     * 匿名内部类
     * 1、Kotlin中的 接口 或者 抽象类创建内部类的方式必须通过 “对象表达式”，不能采用 "lambada表达式"
     * 2、如果 接口 是Java中的接口且该接口仅有一个方法时，可以采用 "lambada表达式"；
     *  如果有多个方法，只能采用对象表达式。
     */
    val window = Window()
    window.setListener(object : MouseListener {
        override fun mouseClicked(){
        }

        override fun mouseEntered() {
        }
    })

    //Java中的单方法接口
    window.setOnClickListener(OnClickListener {
        println(a)
        println("on click is called")
    })

    //Java中的多方法接口.
    window.setOnLongClickListener(object : OnLongClickListener {
        override fun onClick() {
        }

        override fun onLongClick() {
        }
    })

    //对象表达式
    window.setOnClickListener(object : DefaultClickListener("hello world"){

        override fun doSomething(name: String) {

        }
    })

    window.setOnClickListener(CustomClickListener("hello world"))
}

abstract class DefaultClickListener(val name: String): OnClickListener {

    override fun onClick() {
        doSomething(name)
    }

    abstract fun doSomething(name: String)
}

class CustomClickListener(name: String): DefaultClickListener(name){

    override fun doSomething(name: String) {

    }
}

interface MouseListener{
    fun mouseClicked()
    fun mouseEntered()
}

class Window{

    lateinit var mListener: MouseListener

    lateinit var mOnClickListener: OnClickListener

    lateinit var mOnLongClickListener: OnLongClickListener

    fun setOnClickListener(onClickListener: OnClickListener){
        this.mOnClickListener = onClickListener
    }

    fun setOnLongClickListener(onLongClickListener: OnLongClickListener){
        this.mOnLongClickListener = onLongClickListener
    }

    fun setListener(listener: MouseListener){
        this.mListener = listener
    }
}