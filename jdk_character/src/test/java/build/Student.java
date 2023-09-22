

import lombok.Data;

@Data
public class Student{
    private String name;
    private Integer age;
    private String info;

    public void setInfo(String key, String value) {
        if(key.contains(":")){
            throw new RuntimeException(String.format("【%s】包含了 '：'，请确认！", key));
        }
        this.info = String.format("%s : %s", key, value);
    }

}
