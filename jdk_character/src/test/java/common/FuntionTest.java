import lombok.Data;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

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

    @Test
    @DisplayName("Predicate接口")
    public void test2() {

    }

    @Test
    @DisplayName("Function接口")
    public void test3() {

    }

    @Data
    class Student {
        private Integer age;
        private String name;
        private String tag;
    }


}
