import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;

import java.io.IOException;

public class Main2 {
  public static void main(String[] args) throws IOException {
    // 基于脚本文件
    Expression exp =
        AviatorEvaluator.getInstance()
            .compileScript(
                "C:\\Users\\JNPF\\Desktop\\aviator_demo\\aviator_demo\\src\\main\\java\\math.av",
                true);
    Object result = exp.execute(exp.newEnv("a", 9, "b", 1, "type", "sum"));

    System.out.println("加法: " + result);

    result = exp.execute(exp.newEnv("a", 1, "b", 9, "type", "subtraction"));

    System.out.println("减法: " + result);

    result = exp.execute(exp.newEnv("a", 1, "b", 9, "type", "multiplication"));

    System.out.println("乘法: " + result);

    result = exp.execute(exp.newEnv("a", 1, "b", 9, "type", "division"));

    System.out.println("除法: " + result);
  }
}
