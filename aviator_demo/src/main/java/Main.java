import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;

@Slf4j
public class Main {
  public static void main(String[] args) throws IOException {
    // 表达式参数
    HashMap<String, Object> map = new HashMap<>();
    map.put("a", 1);
    map.put("b", 2);
    map.put("c", 3);

    // 2.表达式校验
    String expression = "a-(b-c) > 100";
    try {
      AviatorEvaluator.validate(expression);
      Expression compiledExp = AviatorEvaluator.compile(expression);
      // Execute with injected variables.

      Boolean data = (Boolean) compiledExp.execute(map);
      System.out.println(data);
    } catch (Exception e) {
      log.error(e.toString());
    }

//        expression = "1 +* 2";
//        try {
//          AviatorEvaluator.validate(expression);
//        } catch (Exception e) {
//          log.error(e.toString());
//        }
  }
}
