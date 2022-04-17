import java.util.Optional;

/**
 * null指针封装
 */
public class OptionalTest {
   public  static void getStudent(Student student){
       Optional<Student> optional =  Optional.ofNullable(student);
//       Student student1 = optional.orElse(null);
//       optional.flatMap()
//       optional.map()
//       optional.filter()



       try {
           Student student1 = optional.orElseThrow(MyException::new);



       } catch (MyException e) {
           e.printStackTrace();
       }
   }
    public static void main(String[] args) {
        Student student = new Student();
        student.setName("larry");
        getStudent(null);
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
            return "Student{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
