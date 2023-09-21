package base;

import java.util.Optional;

/**
 * null指针封装
 */
public class OptionalTest {
   public  static void getStudent(Student student){
       Optional<Student> optional =  Optional.ofNullable(student);
       try {
           Student student1 = optional.orElseThrow(MyException::new);
       } catch (MyException e) {
           e.printStackTrace();
       }
   }
    public static void main(String[] args) {
        // 直接抛出异常处理
//        build.Student student = new build.Student();
//        student.setName("larry");
//        getStudent(null);

        // 使用默认值返回
        String name= "123";
        String name1 = Optional.ofNullable(name).orElse("");
        System.out.println(name1);
    }

    private static class MyException extends  Exception{
        public MyException() {
            super();
        }

        public MyException(String message) {
            super(message);
        }

        @Override
        public String getMessage() {
            return "自定义异常";
        }
    }
    private static class Student{
        String name;
        Integer age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "build.Student{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
