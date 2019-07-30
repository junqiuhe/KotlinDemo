package com.java.demo.clazz;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * Project Name：KotlinDemo
 * Created by hejunqiu on 2019/7/30 15:33
 * Description:
 */
public class UseGenericJava {

    public static void main(String[] args) {

        DynamicArray<Integer> ints = new DynamicArray<>();
        DynamicArray<? extends Number> numbers = ints;

        Integer a = 200;

        /**
         * ？ 以及 ？ extends E 有一个重要的限制，指定读取，不能写入。
         *
         * ? extends Number表示是Number的某个子类型，但不知道是具体子类型，如果允许写入，Java无法保证类型安全性。
         */
//        numbers.add(a);
//        numbers.add((Number) a);
//        numbers.add((Object) a);

        useGenericAndArray();
    }

    /**
     * 编译错误，
     */
//    private static void swap(DynamicArray<?> array, int i, int j){
//        Object temp = array.get(i);
//
//        array.set(i, array.get(j));
//        array.set(j, temp);
//    }
    private static <T> void swapInternal(DynamicArray<T> array, int i, int j) {
        T temp = array.get(i);

        array.set(i, array.get(j));
        array.set(j, temp);
    }

    private static void swap(DynamicArray<?> array, int i, int j) {
        swapInternal(array, i, j);
    }


    /**
     * 类型参数之间的依赖关系
     *
     * @param dest
     * @param src
     * @param <D>
     * @param <S>
     */
    private static <D, S extends D> void copy(DynamicArray<D> dest,
                                              DynamicArray<S> src) {
        for (int i = 0; i < src.size; i++) {
            dest.add(src.get(i));
        }
    }

    /**
     * 与 copy 等价。
     *
     * @param dest
     * @param src
     * @param <D>
     */
    private static <D> void copy1(DynamicArray<D> dest,
                                  DynamicArray<? extends D> src) {
        for (int i = 0; i < src.size; i++) {
            dest.add(src.get(i));
        }
    }

    /**
     * 通配符与返回值。
     * <p>
     * 如果返回值依赖于类型参数，不能用通配符。
     *
     * @param array
     * @param <T>
     * @return
     */
    private static <T extends Comparable<T>> T max(DynamicArray<T> array) {
        T max = array.get(0);

        for (int i = 1; i < array.size; i++) {
            if (array.get(i).compareTo(max) > 0) {
                max = array.get(i);
            }
        }

        return max;
    }

    private static void useCopy() {
        DynamicArray<Integer> ints = new DynamicArray<>();
        ints.add(1);
        ints.add(2);

        DynamicArray<Number> numbers = new DynamicArray<>();

        ints.copyTo(numbers);
    }

    public static class DynamicArray<E> {

        private static final int DEFAULT_CAPACITY = 10;

        private int size;

        private Object[] elementData;

        public DynamicArray() {
            this.elementData = new Object[DEFAULT_CAPACITY];
        }

        private void ensureCapacity(int minCapacity) {
            int oldCapacity = elementData.length;
            if (oldCapacity >= minCapacity) {
                return;
            }

            int newCapacity = oldCapacity * 2;
            if (newCapacity < minCapacity) {
                newCapacity = minCapacity;
            }
            elementData = Arrays.copyOf(elementData, newCapacity);
        }

        public void add(E e) {
            ensureCapacity(size + 1);
            elementData[size++] = e;
        }

        public E get(int index) {
            return (E) elementData[index];
        }

        public int getSize() {
            return this.size;
        }

        public E set(int index, E element) {
            E oldValue = get(index);
            elementData[index] = element;
            return oldValue;
        }

        public void copyTo(DynamicArray<? super E> dest) {
            for (int i = 0; i < size; i++) {
                dest.add(get(i));
            }
        }
    }

    /**
     * 类型擦除可能会引发一些冲突
     */

    private static class Base implements Comparable<Base> {
        @Override
        public int compareTo(@NotNull Base o) {
            return 0;
        }
    }

    /**
     * Java 编译器会提示错误，Comparable接口不能被实现两次，且两次实现的类型参数还不同，
     * 一次是Comparable<Base>，一次是Comparable<Child>。
     *
     * 为什么不允许呢？因为类型擦除后，实际上只能有一个。
     */
//    private static class ChildError extends Base implements Comparable<ChildError>{
//        @Override
//        public int compareTo(@NotNull ChildError o) {
//            return 0;
//        }
//    }

    /**
     * 只能重写compareTo方法.
     */
    private static class Child extends Base {

        @Override
        public int compareTo(@NotNull Base o) {
            if (!(o instanceof Child)) {
                throw new IllegalArgumentException();
            }
            Child c = (Child) o;
            //...
            return 0;
        }
    }


    /**
     * 泛型与数组
     */
    private static void useGenericAndArray() {
        /**
         * 不能创建泛型数组，因此该写法是错误的
         */
//        Pair<Object, Integer>[] options = new Pair<Object, Integer>[]{
//            new Pair("1元", 7),
//            new Pair("2元", 2),
//            new Pair("10元", 1)
//        };

        /**
         * 数组是协变的，Java中泛型不是协变的。
         * 因此，DynamicArray<Integer> 声明的变量不能赋值给 DynamicArray<Number>,
         * 但是数组可以。
         * Integer[] ints = new Integer[10];
         * Number[] numbers = ints;
         * Object[] objs = numbers;
         */

        /**
         * 虽然Java允许这种转换，但是如果使用不当，可能会引发运行时异常。如：
         */
        Integer[] ints = new Integer[10];
        Object[] objs = ints;
        objs[0] = "hello";

        /**
         *
         * 如果以下代码正确编译的话，那么将会可能到运行时异常。Java编译器为了避免这种情况，因此禁止这种行为。
         *
         * Pair<Object, Integer>[] options = new Pair<Object, Integer>[3];
         * Object[] objs1 = options;
         * objs1[0] = new Pair<Double, String>(12.34, "hello");
         */


    }

    private static class Pair<T, U> {
        private T first;
        private U second;

        public Pair(T first, U second) {
            this.first = first;
            this.second = second;
        }

        public T getFirst() {
            return this.first;
        }

        public U getSecond() {
            return this.second;
        }
    }
}