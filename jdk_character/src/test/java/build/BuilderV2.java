


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class BuilderV2<T> {

    /**
     * 存储调用方 指定构造类的 构造器
     */
    private final Supplier<T> constructor;
    /**
     * 存储 指定类 所有需要初始化的类属性
     */
    private final List<Consumer<T>> dInjects = new ArrayList<>();

    private Consumer head = o -> {

    };

    // V2: 构造函数私有化
    private BuilderV2(Supplier<T> constructor) {
        this.constructor = constructor;
    }

    public static <T> BuilderV2<T> builder(Supplier<T> constructor) {
        return new BuilderV2<>(constructor);
    }

    public <P1> BuilderV2<T> with(BuilderV2.DInjectConsumer<T, P1> consumer, P1 p1) {
        Consumer<T> c = instance -> consumer.accept(instance, p1);
        head = head.andThen(c);
        return this;
    }

    public <P1> BuilderV2<T> with(BuilderV2.DInjectConsumer<T, P1> consumer, P1 p1, Predicate<P1> predicate) {
        if (null != predicate && !predicate.test(p1)) {
            throw new RuntimeException(String.format("【%s】参数不符合通用业务规则！", p1));
        }
        Consumer<T> c = instance -> consumer.accept(instance, p1);
        head = head.andThen(c);
        return this;
    }


    public <P1, P2> BuilderV2<T> with(BuilderV2.DInjectConsumer2<T, P1, P2> consumer, P1 p1, P2 p2) {
        Consumer<T> c = instance -> consumer.accept(instance, p1, p2);
//        dInjects.add(c);
        head = head.andThen(c);
        return this;
    }

    public T build() {
        // 调用supplier 生成类实例
        T instance = constructor.get();
        // 调用传入的setter方法，完成属性初始化
        head.accept(instance);
        // 返回 建造完成的类实例
        return instance;
    }

    @Test
    public void test() {
        Student student = BuilderV2.builder(Student::new)
                .with(Student::setName, "张三:", (str) -> str.contains(":"))
                .with(Student::setAge, 18)
                .with(Student::setInfo, "自我介绍:", "我是一个活泼开朗的男孩")
                .build();


        System.out.println();

    }

    @FunctionalInterface
    public interface DInjectConsumer<T, P1> {
        void accept(T t, P1 p1);
    }


    @FunctionalInterface
    public interface DInjectConsumer2<T, P1, P2> {
        void accept(T t, P1 p1, P2 p2);
    }

}
