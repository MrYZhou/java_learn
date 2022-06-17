package stream;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

public class GroupBy {
  public static void main(String[] args) {
    // list to map

    ArrayList<Student> objects = new ArrayList<Student>(){{
      add(new Student("张三",10));
      add(new Student("李四",10));
      add(new Student("李四",20));
      add(new Student("李四",30));
    }};
    // 第三个形参是合并冲突使用
    Map<String, Integer> map = objects.stream().collect(Collectors.toMap(Student::getName, Student::getAge,(k1,k2)->k2) );
    System.out.println(map);
    System.out.println(map.get("李四"));
  }
  @Data
  @AllArgsConstructor
  static
  class  Student {
    String name;
    Integer age;
  }
  /**
   * 使用分组
   * Map<String, List<RoleOrgEntity>> projectMap = list.stream().filter(t ->"-1".equals(t.getOrganizeId()) ).collect(Collectors.groupingBy(RoleOrgEntity::getName));
   */
}
