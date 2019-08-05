package com.kotlin.demo.other

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/8/5 9:48
 * Description:
 */

/**
 * 限定的this
 *
 * 要访问来自外部作用域的this（一个类或者扩展函数，或者带标签的带有接收者的函数字面值）
 * 我们使用this@label，其中@label是一个代指this来源的标签。
 */
fun main(array: Array<String>) {

    val b = A().B()

    b.printlnB()

    val persons = listOf(Person("Alice", 29), Person("Bob", 31))
    lookForAliceVersion1(persons)

    println("-----------------------------------")

    lookForAliceVersion2(persons)

    println("-----------------------------------")
    lookForAliceVersion3(persons)
}

fun lookForAliceVersion1(persons: List<Person>) {
    for (person in persons) {
        if (person.name == "Alice") {
            println("Found!!!")
            return
        }
    }

    println("Alice is not found!")
}

/**
 * 如果你在lambda使用return 关键字，它会从调用lambda的函数返回，并不只是从lambda中返回。
 * 这样的return语句叫作非局部返回。
 *
 * 只有在以lambda作为参数的函数是 “内联函数”的时候才能从更外层的函数返回。
 * 在一个非内联函数的lambda中使用return表达式是不允许的。
 */
fun lookForAliceVersion2(persons: List<Person>) {

    /**
     * 从外部返回。
     */
    persons.forEach { person: Person ->
        if (person.name == "Alice") {
            println("Found!!!")
            return
        }
    }
    println("Alice is not found!")

    /**
     * 输出结果为 Found!!
     */
}


fun lookForAliceVersion3(persons: List<Person>) {
    //    persons.forEach1{ person: Person ->
//        if (person.name == "Alice") {
//            println("Found!!!")
////            return //compile error, 因为forEach1不是内联函数。
//        }
//    }

    /**
     * 相当于从局部函数返回。
     */
//    persons.forEach1 forEach1@{ person: Person ->
//        println(person)
//        if (person.name == "Alice") {
//            println("Found!!")
//
//            return@forEach1
//        }
//    }
//    println("Alice is not found!")

    persons.forEach (fun(person){
        println(person)
        if (person.name == "Alice") {
            println("Found!!")

            return
        }
    })
    println("Alice is not found!")

    persons.forEach forEach@{ person: Person ->
        println(person)
        if (person.name == "Alice") {
            println("Found!!")

            return@forEach
        }
    }
    println("Alice is not found!")

    /**
     * 输出结果为 Alice
     *          Found!!
     *          Bob
     *          Alice is not found!
     */
}

fun <T> Iterable<T>.forEach1(action: (T) -> Unit) {
    for (element in this) {
        action(element)
    }
}

data class Person(val name: String, val age: Int)

class A {
    inner class B {

        fun Int.foo() {
            val a = this@A  //A的this
            println(a)

            val b = this@B  //B的this
            println(b)

            val c = this  //foo的接受者，一个Int
            val c1 = this@foo  // foo的接收者，一个Int

            println(c)
            println(c1)

            val funLit = lambda@ fun String.() {
                /**
                 * 接收者是 String，因此this作用域指向的是接收者对象。
                 */
                val d = this@lambda  //funLit 的接受者

                println(d)
            }

            val test = "hello world"
            test.funLit()

            val funLit2 = { s: String ->
                /**
                 * 因为是lambda表达式，没有任何的接收者，因此它指向的是 Int.
                 */
                val d1 = this
                println(d1)
            }

            funLit2("hello world")

            val funLit3 = outerLambda@ fun String.() {

                println(this@outerLambda)

                val funLit4 = innerLambda@ fun String.() {
                    println(this@innerLambda)
                }

                "innerLambda".funLit4()
            }

            "outerLambda".funLit3()
        }

        fun printlnB() {
            val a = 123
            a.foo()
        }
    }
}