package stream;

import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IndexTest {
    static ArrayList<Student> list = new ArrayList<Student>() {{
        add(new Student("1","张三", 10, "code1"));
        add(new Student("2","李四", 10, "code2"));
        add(new Student("3","李四", 10, "code1"));
        add(new Student("4","李四", 20, "code2"));
        add(new Student("5","李四", 30, "code5"));
    }};

    @Test
    @DisplayName("Stream根据对象某个属性去重")
    public void test1() {
        ArrayList<Student> result = list.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(
                () -> new TreeSet<>(Comparator.comparing(Student::getAge))), ArrayList::new));
        System.out.println(result);
    }

    @Test
    @DisplayName("测试转key,map")
    public void testStudent() {
        // 第三个形参是合并冲突使用
        Map<String, Integer> map = list.stream().collect(Collectors.toMap(Student::getName, Student::getAge, (k1, k2) -> k2));
        System.out.println(map);
        System.out.println(map.get("李四"));
    }

    @Test
    @DisplayName("测试转key,map")
    public void testStudent2() {
        Map<String, Student> orgMap = Optional.ofNullable(list).orElse(new ArrayList<>())
                .stream().collect(Collectors.toMap(Student::getId, Function.identity()));
        Student student = orgMap.get("1");
        System.out.println(student.getName());
    }



    @Test
    @DisplayName("测试分组1")
    public void testgroup1() {
        // 先把10岁的收集后在按名字分组
        Map<String, List<Student>> projectMap = list.stream().filter(t -> "10".equals(t.getAge())).collect(Collectors.groupingBy(Student::getName));
    }

    @Test
    @DisplayName("测试分组2")
    public void testgroup2() {
        // 先把排序码大小的先分组，没有排序码的优先级最低
        HashMap<String, Integer> map = new HashMap<>();
        map.put("code1", 1);
        map.put("cod2", 2);
        Map<String, List<Student>> collect = list.stream().sorted(Comparator.comparing(t -> {
            Integer result = map.get(t.getOrder());
            return result == null ? 9999 : result;
        })).collect(Collectors.groupingBy(Student::getOrder));
        System.out.println(collect);
    }

    @Test
    @DisplayName("测试生成数组")
    public void testStudent3() {
        List<String> list = Stream.generate(() -> 8).limit(3).map(String::valueOf).collect(Collectors.toList());
        System.out.println(String.join(", " , list));
        // jdk1.8
        System.out.println(Stream.generate(() -> 8).limit(3).map(String::valueOf).collect(Collectors.toList()));
        // jdk17
        System.out.println(Stream.generate(() -> 8).limit(3).map(String::valueOf).toList());
    }


    @Data
    @AllArgsConstructor
    static class Student {
        String id;
        String name;
        Integer age;
        String order;

        @Override
        public String toString() {
            return
                    "name='" + name +
                            ", age=" + age +
                            ", order='" + order + "\n";
        }
    }
}
