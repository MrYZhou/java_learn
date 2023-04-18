package stream;



import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.*;
import java.util.stream.Collectors;


public class GroupBy {
  static ArrayList<Student> list = new ArrayList<Student>(){{
    add(new Student("张三",10));
    add(new Student("李四",10));
    add(new Student("李四",10));
    add(new Student("李四",20));
    add(new Student("李四",30));
  }};

  @Test
  @DisplayName("测试转key,map")
  public void testStudent(){
    // 第三个形参是合并冲突使用
    Map<String, Integer> map = list.stream().collect(Collectors.toMap(Student::getName, Student::getAge,(k1,k2)->k2) );
    System.out.println(map);
    System.out.println(map.get("李四"));
  }


  @Test
  @DisplayName("测试分组")
  public void testStudent2(){
    Map<String, List<Student>> projectMap = list.stream().filter(t ->"10".equals(t.getAge()) ).collect(Collectors.groupingBy(Student::getName));
  }


  @Data
  @AllArgsConstructor
  static
  class  Student {
    String name;
    Integer age;
  }
}
