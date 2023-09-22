
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Builder<T> {

    /**
     * 初始化属性的方法
     */
    private final Supplier<T> constructor;
    /**
     * 需要初始化的属性
     */
    private final List<Consumer<T>> dInjects = new ArrayList<>();


    public Builder(Supplier<T> constructor) {
        this.constructor = constructor;
    }

    public static <T> Builder<T> builder(Supplier<T> constructor) {
        return new Builder<>(constructor);
    }

    public static void main(String[] args) {
        Student student = Builder.builder(Student::new).with(Student::setName, "张三" ).with(Student::setAge, 18).build();
        System.out.println(student);
    }

    public <p> Builder<T> with(Builder.DInjectConsumer<T, p> consumer, p p) {
        Consumer<T> c = instance -> consumer.accept(instance, p);
        dInjects.add(c);
        return this;
    }

    @Test
    public void test() {
        Builder<Student> aa = Builder.builder(Student::new).with(Student::setName, "张三" );

//        Student student = Builder.builder(Student::new).with(Student::setName, "张三").with(Student::setAge, 18).build();
//        System.out.println(student);
//        Builder<Student> builder = Builder.builder(Student::new);

    }

    public T build() {
        // 调用supplier 生成类实例
        T instance = constructor.get();
        // 调用传入的setter方法，完成属性初始化
        dInjects.forEach(dInject -> dInject.accept(instance));
        // 返回 建造完成的类实例
        return instance;
    }

    @FunctionalInterface
    public interface DInjectConsumer<T, p> {
        void accept(T t, p p);
    }

}
