import lombok.Data;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class FuntionTest {
    /**
     * Consumer接口是一个函数式接口，用于表示一个接受单个输入参数并且不返回结果的操作。
     * 它定义了一个名为accept的抽象方法，该方法接受一个泛型参数并且没有返回值。
     * Consumer接口通常用于执行一些操作，例如打印、修改对象状态等，而不是返回结果。
     */
    @Test
    @DisplayName("Consumer接口")
    public void test1() {
        Consumer<Student> consumer = student -> student.setTag("xfx");
        Student student = new Student();
        consumer.accept(student);
        System.out.println(student.getTag());
    }

    public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T t : list) {
            if (predicate.test(t)) {
                result.add(t);
            }
        }
        return result;
    }

    public static int calculateBirthYear(Student student, Function<Integer, Integer> ageToBirthYear) {
        return ageToBirthYear.apply(student.getAge());
    }

    /**
     * Predicate接口是Java中的一个函数式接口，它定义了一个名为test的抽象方法，该方法接受一个参数并返回一个布尔值。
     * Predicate接口常用于判断某个对象是否满足某个条件，可以用于过滤、筛选、验证等场景。
     */
    @Test
    @DisplayName("Predicate接口")
    public void test2() {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        numbers.add(5);
        // 使用Predicate过滤List中的偶数,并返回
        Predicate<Integer> isEven = number -> number % 2 == 0;
        List<Integer> evenNumbers = filter(numbers, isEven);

        System.out.println("原始列表：" + numbers);
        System.out.println("偶数列表：" + evenNumbers);
    }

    /**
     * Function接口是一个函数式接口，
     * 它定义了一个具有一个输入和一个输出的函数。该接口包含一个抽象方法apply()，该方法接收一个参数并返回一个值。其定义如下：
     *
     * @FunctionalInterface public interface Function<T, R> {
     * R apply(T t);
     * }
     * 其中T是输入类型，R是输出类型。
     * 函数式编程强调函数作为一等公民，将函数作为值进行传递和操作，而Lambda表达式则是一种声明函数的语法。
     */
    @Test
    @DisplayName("Function接口")
    public void test3() {
        // 根据Student对象的年龄属性计算其出生年份
        Student student = new Student();
        student.setName("larry");
        student.setAge(25);
        int birthYear = calculateBirthYear(student, age -> LocalDate.now().getYear() - age);
        System.out.println(student.getName() + " was born in " + birthYear);
    }

    @Data
    class Student {
        private Integer age;
        private String name;
        private String tag;
    }


}
