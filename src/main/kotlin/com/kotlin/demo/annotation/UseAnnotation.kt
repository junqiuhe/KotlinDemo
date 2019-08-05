package com.kotlin.demo.annotation

import java.text.SimpleDateFormat
import java.util.*
import kotlin.reflect.KClass

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/7/31 9:32
 * Description:
 */


/**
 * Kotlin 中使用注解的方法和Java一样。
 * 要应用一个注解，以@字符作为（注解）名字的前缀，并放在要注解的声明最前面。
 * 可以注解不同的代码元素，比如函数和类
 */

/**
 * 实参在括号中传递，就和常规函数调用一样。
 */
@Deprecated(
    message = "Use removeAt(index) instead.",
    replaceWith = ReplaceWith("removeAt(index)")
)
fun remove(index: Int) {
}

fun removeAt(index: Int) {
}

fun useRemove() {
    remove(1)
}

//----------------------------------------
/**
 * 一、应用注解
 * 注解的类型参数限制：
 *      1、基本数据类型
 *      2、字符串
 *      3、枚举
 *      4、类引用
 *      5、其他的注解类
 *      6、前面这些类型的数组。
 *
 * 注意点：
 *      1、要把一个类指定我注解实参，需在类名后加上::class，如 @MyAnnotation(MyClass:class)
 *      2、要把另一个注解指定为一个实参，去掉注解名称前面的 @
 *      3、要把一个数组指定为一个实参，使用 arrayOf函数。如 @RequestMapping(path = arrayOf("/foo", "/bar"))
 *
 * 注解实参需要在编译器就是已知的，属于不能引用任意的属性作为实参。
 * 要把属性当作注解实参使用，你需要用 const 修饰符标记它，来告知编译器这个属性是编译器常量。
 *
 * 二、注解目标
 * 许多情况下，Kotlin源代码中的单个声明会对应成多个Java声明，而且它们每个都能携带注解。
 * 如：一个Kotlin属性就对应一个 Java字段、一个getter，以及一个潜在的setter 和 它的参数。
 * 而一个在主构造方法中声明的属性还多用一个一个对应的元素：构造方法的参数。因此，说明这些元素中哪些需要注解是否主要。
 *
 * 使用 点目标声明 被用来说明要注解的元素。使用 点目标 被放在 @符号 和 注解名称之间，并用冒号和注解名称隔开，
 * 如 @get:Rule
 *
 * Kotlin 点目标 完整列表：
 *      1、property ---- Java的注解不能应用这种使用点目标
 *      2、field ---- 为属性生成的字段
 *      3、get ---- 属性的 getter
 *      4、set ---- 属性的 setter
 *      5、receiver ---- 扩展函数或者扩展属性的接受者参数
 *      5、param ---- 构造方法的参数
 *      6、setparam ---- 属性 setter的参数
 *      7、deletegate ---- 为委托属性存储委托实例的字段
 *      8、file ---- 包含在文件中声明的顶层函数和属性的类
 *
 * 注意：和Java不一样的是，Kotlin允许你对任意表达式应用注解，而不仅仅是类和函数的声明及类型。
 *
 */
fun test(list: List<*>) {

    @Suppress("UNCHECKED_CAST")
    val strings = list as List<String>
}

/**
 * 三、使用注解定制JSON序列化。
 *  参考 JKid 项目
 *
 *
 * 四、声明注解
 *
 *  //声明没有任何参数的注解
 *  annotation class JsonExclude
 *
 *  //声明有参数的注解
 *  annotation class JsonName(val name: String)
 *
 * 五、元注解
 * 元注解：可以应用到注解类上的注解称作为元注解。
 *      1、@Target ---> 说明了注解可以被应用的元素类型。如果不使用它，所有的声明都可以应用这个注解。
 *      2、@Retention ---> 声明注解的生命周期。
 *          Java中默认会在.class文件中保留注解但是不会让他们在运行时访问到。
 *          Kotlin的默认行为不同：注解拥有 RUNTIME 保留期。
 */

@Target(AnnotationTarget.PROPERTY)
annotation class JsonExclude


/**
 * 可声明自己的元注解
 */
@Target(AnnotationTarget.ANNOTATION_CLASS)
annotation class BindingAnnotation

@BindingAnnotation
annotation class MyBinding

/**
 * 六、使用类做注解参数
 */

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.PROPERTY)
annotation class DeserializeInterface(val targetClass: KClass<out Any>)

data class Company(val name: String)

//Person 类实例嵌套的 company 对象。
data class Person(
    val name: String,
    @DeserializeInterface(Company::class) val company: Company
)

/**
 * 七、使用泛型类做注解参数
 */

interface ValueSerializer<T> {
    fun toJsonValue(value: T): Any?
    fun fromJsonValue(jsonValue: Any?): T
}

object DateSerializer : ValueSerializer<Date> {

    private val dateFormat = SimpleDateFormat("dd-mm-yyyy")

    override fun toJsonValue(value: Date): Any? =
        dateFormat.format(value)

    override fun fromJsonValue(jsonValue: Any?): Date =
        dateFormat.parse(jsonValue as String)
}

@Target(AnnotationTarget.PROPERTY)
annotation class CustomSerializer(val serializerClass: KClass<out ValueSerializer<*>>)

data class CustomPerson(
    val name: String,
    @CustomSerializer(DateSerializer::class) val date: Date
)

fun main(array: Array<String>) {
}