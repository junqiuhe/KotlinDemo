package com.kotlin.demo.clazz

import kotlin.Exception
import kotlin.random.Random
import kotlin.reflect.KClass

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/7/29 15:18
 * Description:
 */

/**
 * 一、声明泛型函数，属性以及泛型类
 *
 * 1、泛型函数
 *      fun <T> List<T>.slice(indices: IntRange): List<T>{
 *          //...do something.
 *      }
 *
 * 2、对扩展属性声明泛型
 *      val <T> List<T>.penulimate: T
 *          get() = this[size - 2]
 *  注意：不能声明泛型非扩展属性。
 *
 *  3、声明泛型类 / 接口
 *
 *  4、类型参数约束
 *      fun <T: Number> List<T>.sum(): T{
 *          //do something.
 *      }
 *
 *  5、让参数类型非空
 *      默认情况下，没有指定上界的类型形参将会使用 Any?
 *
 *      class Processor<T>{
 *          fun process(value: T){
 *              println(value?.hashCode())
 *          }
 *      }
 */

class Processor<T> {
    fun process(value: T) {
        println(value?.hashCode())
    }
}

fun useProcessor() {
    val nullProcessor = Processor<String?>()
    nullProcessor.process(null)
}

//-------------------------------------------
/**
 * 如果让参数类型非null，必须显示指定一个任意非空上界，不光是Any, 如：
 * class Processor1<T: Any>{
 *      fun process(value: T){
 *          println(value.hashCode())
 *      }
 * }
 */
class Processor1<T : Any> {
    fun process(value: T) {
        println(value.hashCode())
    }
}

fun useProcessor1() {
//   val processor = Processor1<String?>() //compile error.

    val processor = Processor1<String>()
    processor.process("hello world")
}

//----------------------------------

/**
 * 二、运行时泛型：泛型擦除 和 实化类型参数
 *
 *      JVM上的泛型一般都是通过类型擦除实现的，就是说泛型类实例的类型实参在运行时是不保留的。
 *
 *      在Kotlin中，可以声明一个 inline 函数，使其类型实参不被擦除（实化）。
 *
 *  1）、运行时的泛型：类型检查和转换
 *      与Java一样，Kotlin的泛型在运行时也被擦除了，这意味着泛型类实例不会携带创建它的类型实参类型。
 *      如：如果你创建了一个 List<String> 并将一堆字符串放入其中，在运行时你只能看到它是一个List，
 *      不能识别出列表本打算包含的是哪种类型的元素
 *
 *      val list1: List<String> = listOf("a", "b")
 *      val list2: List<Int> listOf(1, 2, 3)
 *      在运行时，你不知道list1和list2是否声明成字符创或者整数列表。它们每个都只是List.
 *
 *      正因为 泛型擦除类型信息的约束。你不能在运行时判断一个列表是一个包含字符串的列表还是包含其他对象的列表。
 *      因此，以下代码编译不过：
 *
 *      if(value is List<String>) //compile error.
 *
 *      Kotlin 不允许使用没有指定类型实参的泛型类型，那么你可能想知道如何检查一个值 是否是列表，而不是 set 或者其他对象。可以使用特殊的“星号投影”语法来做这种检查
 *
 *      if(value is List<*>){...}
 *
 * 2）、声明带实化类型参数的函数。
 *
 *      在调用泛型函数的时候，在函数体中你不能决定调用它用的类型参数
 *
 *      fun <T> isA(value: Any) = value is T // compile error.
 *
 *      场景一：
 *      inline fun <reified T> isA(value: Any) = value is T  //ok
 *
 *      场景二：使用实化类型参数代替类引用
 *      inline fun <reified T: Activity> Activity.startActivity(){
 *          val intent = Intent(this, T::class.java)
 *          startActivity(intent)
 *      }
 */

/**
 * as 与 as?
 *
 * 如果该类有正确的基础类型但类型实参是错误的，装换也不会失败，因为在运行时转换发送的时候类型实参是未知的
 *
 */
fun printlnSum(c: Collection<*>) {
    val intList = c as? List<Int> ?: throw IllegalArgumentException("List is expected")

    println(intList.sum())
}

/**
 * 对已知类型实参做类型转换是合法的
 */
fun printlnSum1(c: Collection<Int>) {

    // 是合法的，因为在编译器确定了结合包含的整型数字
    if (c is List<Int>) {
        println(c.sum())
    }
}

//fun <T> isA(value: Any) = value is T
//Error: Cannot check for instance of erased type : T

inline fun <reified T> isA(value: Any) = value is T


//----------------------------------
/**
 * 三、变型：泛型与子类型化
 *
 * 变型的概念描述了拥有相同基础类型和不同类型实参的（泛型）类型之间是如何关联的。
 * 如，List<String> 和 List<Any> 之间如何关联。
 *
 * 1）、为什么存在变型：给函数传递实参
 *      把一个字符串列表传递给Any对象列表的函数是否安全？
 *      如果函数添加或者替换了列表中的元素就是不安全的，因为这样会产生类型不一致的可能性。
 *
 *      如：
 *      fun addAnswer(list: MutableList<Any>){
 *          list.add(42)
 *      }
 *
 *      val strings = mutableListOf("abc", "bac")
 *      addAnswer(strings)   // 如果这一行编译通过了，
 *      println(strings.maxBy{it.length})  //运行时就会产生一个异常
 *
 * 2）、类、类型和子类型
 *      变量的类型规定了该变量的可能值。有时候我们会把类型和类当成同样的概念使用。但它们是不一样的。
 *
 *      非泛型类：类的名称可以直接当做类型使用。
 *      val x: String 就是声明了一个可以保存String类的实例的变量
 *      val x: String?  同样的类名称也可以用来声明可空类型
 *
 *      泛型类：要得到一个合法的类型，需要用一个作为类型实参的具体类型替换（泛型）类的类型形参。
 *      因此List不是一个类型（它是一个类），List<Int>、List<String?>、List<List<String>>等是一系列合法的类型。
 *
 *  子类型：任何时候如果需要的是 类型A 的值，都能够使用 类型B 来代替，那么 类型B 称为 类型A 子类型。
 *
 *  简单情况下，子类型和子类本质上意味着一样的事物。
 *  如Int类是Number的子类，因此Int类型是Number类型的子类型。
 *  如果一个类实现了一个接口，它的类型就是该接口类型的子类型，如String是CharSequence的子类型。
 *
 *  注：非空类型 A 是 可控类型 A? 的子类型。
 *
 *  涉及泛型类型时，子类型和子类之间的差异显得格外重要。
 *
 *  List<String> 是 List<Any> 的子类型吗？
 *
 *      我们已经了解到为什么把MutableList<String>当成MutableList<Any>的子类型对待是不安全的。
 *
 * 一个泛型类 --- 例如：MutableList --- 如果对于任意两种类型 A 和 B，
 *
 * MutableList<A>既不是MutableList<B>的子类型也不是它的超类型，它就被称为在该类型参数上是不变型的。
 *
 * 如果A是B的子类型，那么List<A>就是List<B>的子类型，这样的类或者接口被称为 “协变的”。
 *
 * 3）、协变：保留子类型化关系
 *  Kotlin中，要声明类在某个类型参数上是可以协变的，在该类型参数的名称前加上Out关键字即可
 *
 *  interface Producer<Out T>{   //类被声明成在T上协变。
 *      fun produce(): T
 *  }
 *
 *  类型参数T上的关键字out有两层含义
 *  1、子类型化被保留（Producer<Cat> 是 Producer<Animal> 的子类型）
 *  2、T只能用在 out位置
 *
 *  4）、逆变：反转子类型化关系
 *
 *  interface Consumer<in T>{
 *      fun invoke(value: T)
 *  }
 *  如果B是A的子类型，那么Consumer<A>是Consumer<B>的子类型。类型参数A和B交换了位置。
 *  如Consumer<Animal>就是Consumer<Cat>的子类型。
 *
 *  5、使用点类型：在类型出现的地方指定变型  ---- 类型投影.
 *
 *  MutableList<out T> 和 Java中的 MutableList<? extends T> 作用相同
 *  MutableList<in T> 和 Java中的 MutableList<? super T> 作用相同
 *
 *
 *  6、星号投影：使用 * 代替类型参数
 *
 *  可以用星号投影语法来表明你不知道到关于泛型实参的任何信息。
 *
 *  注意：MutableList<*> 与 MutableList<Any?> 不一样。
 *
 *  MutableList<Any?> 这种类型包含的是任意类型的元素。
 *  而MutableList<*> 包含的是特定类型的元素列表，只是你不知道是哪种类型。
 */

open class Animal {
    fun feed() {
    }
}

class Cat : Animal() {
    fun cleanLitter() {
    }
}

class Herd<out T : Animal> {

    private val mutableList: MutableList<T> = mutableListOf()

    val size: Int get() = mutableList.size

    operator fun get(i: Int): T {
        return mutableList[i]
    }

    fun indexOf(element: @UnsafeVariance T): Int {
        return mutableList.indexOf(element)
    }
}

fun feedAll(animals: Herd<Animal>) {
    for (i in 0 until animals.size) {
        animals[i].feed()
    }
}

fun takeCareOfCats(cats: Herd<Cat>) {
    for (i in 0 until cats.size) {
        cats[i].cleanLitter()
    }
    feedAll(cats)
}


//-----------------------------------------
class Producer<out T>(private val value: T) {

    fun product(): T {
        return value
    }
}

fun useProducer() {
    val producer: Producer<Animal> = Producer(Cat())
    println(producer.product())
}

class Consumer<in T> {
    fun consum(value: T) {
        println(value)
    }
}

fun useConsumer() {
    val cat: Consumer<Cat> = Consumer<Animal>()
    cat.consum(Cat())
}

//-------------------------------------
/**
 * 使用点类型：在类型出现的地方指定变型
 */
fun <T> copyData(
    source: MutableList<out T>,
    destination: MutableList<in T>
) {
    for (item in source) {
        destination.add(item)
    }
}

fun <T : R, R> copyData1(
    source: MutableList<T>,
    destination: MutableList<R>
) {
    for (item in source) {
        destination.add(item)
    }
}

fun useCopyData() {

    //协变
    val source: MutableList<out Animal> = mutableListOf<Cat>()

    //逆变
    val destination: MutableList<in Cat> = mutableListOf<Animal>()

    copyData(mutableListOf<Cat>(), mutableListOf<Animal>())
    copyData1(mutableListOf<Cat>(), mutableListOf<Animal>())
}

//------------------------------------------
/**
 * 星号投影：使用 * 代替类型参数
 *
 * 在该例子中，MutableList<*> 投影成了 MutableList<Any?>: 当你没有任何元素类型信息的时候，读取Any?类型的元素仍是安全的，但是向列表中写入元素是不安全的。
 *
 * 对于 Consumer<in T>这样的逆变类型参数来说，星号投影等价于 <in Nothing>
 *
 * Kotlin MyType<*> 对应于 Java中的 MyType<?>
 *
 * 星号投影的语法很简洁，但只能用在对泛型类型实参的确切值不感兴趣的地方：只是使用生产值的方法，而且不关心那些值的类型。
 *
 */
fun useUnknownType() {
    val list: MutableList<Any?> = mutableListOf('a', 1, "qwe")
    val chars = mutableListOf('a', 'b', 'c')

    val unknownElement: MutableList<*> =
        if (Random.nextBoolean()) list else chars
//    unknownElement.add(43)  //compile error. 编译器禁止调用这个方法

    println(unknownElement.first()) //读取元素是安全的，
}


//--------------------------
interface FieldValidator<in T> {
    fun validate(input: T): Boolean
}

object DefaultStringValidator : FieldValidator<String> {

    override fun validate(input: String): Boolean {
        return input.isNotEmpty()
    }
}

object DefaultIntValidator : FieldValidator<Int> {
    override fun validate(input: Int): Boolean {
        return input >= 0
    }
}

fun useValidator() {
    val validators = mutableMapOf<KClass<*>, FieldValidator<*>>()
    validators[String::class] = DefaultStringValidator
    validators[Int::class] = DefaultIntValidator

    /**
     * compile error. 因为把具体类型的值传给未知类型的验证器是不安全的
     */
//    validators[String::class]!!.validate("")

    //方式一、这种解决方法不是类型安全的，而且容易出错。
    var stringValidator = validators[String::class] as FieldValidator<String>
    println(stringValidator.validate(""))

    stringValidator = validators[Int::class] as FieldValidator<String>
    try {
        stringValidator.validate("")
    } catch (e: Exception) {
        e.printStackTrace()
    }

    /**
     * 所有不安全的逻辑都被隐藏在类的主体中，通过把这些逻辑局部化，保证了它不会被错误使用。
     *
     * 这种模式可以轻松的推广到任意自定义泛型类的存储。把不安全的代码局部化到一个分开的位置上预防误用。
     */
    //方式二
    Validators.registerValidator(String::class, DefaultStringValidator)
    Validators.registerValidator(Int::class, DefaultIntValidator)

    println(Validators[String::class].validate("kotlin"))
    println(Validators[Int::class].validate(42))

    println(Validators[Float::class].validate(input = 42f))
    /**
     * 编译器禁止使用错误的验证器。
     */
//    println(Validators[String::class].validate(42))
}

//方式二、封装起来
object Validators {
    private val validator = mutableMapOf<KClass<*>, FieldValidator<*>>()

    fun <T : Any> registerValidator(kClass: KClass<T>, fieldValidator: FieldValidator<T>) {
        validator[kClass] = fieldValidator
    }

    @Suppress("UNCHECKED_CAST")
    operator fun <T : Any> get(kClass: KClass<T>): FieldValidator<T> =
        validator[kClass] as? FieldValidator<T>
            ?: throw IllegalArgumentException("No validator for ${kClass.simpleName}")
}

fun main(array: Array<String>) {
    useProcessor()

    useProcessor1()

    printlnSum(listOf(1, 2, 3, 4))

    try {
        printlnSum(setOf(1, 2, 3))
    } catch (e: Exception) {
        e.printStackTrace()
    }

    try {
        printlnSum(listOf("a", "b", "c"))
    } catch (e: Exception) {
        e.printStackTrace()
    }

    println(isA<String>("abc"))

    println(isA<String>(123))

    useUnknownType()

    useValidator()


    val numbers: DynamicArray<Number> = DynamicArray()
    val ints: DynamicArray<Int> = DynamicArray()

    numbers.addAllVersion1(ints)
    numbers.addAllVersion2(ints)
}

class DynamicArray<E>{

    /**
     * version1 与 version2等价。
     */
    fun addAllVersion1(c: DynamicArray<out E>){
    }
    fun <T: E> addAllVersion2(c: DynamicArray<T>){
    }
}