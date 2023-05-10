import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import org.junit.jupiter.api.DisplayName;
import org.testng.annotations.Test;

import java.util.HashMap;

public class Main {
    @Test
    @DisplayName("aa")
    public void test1() throws Exception {
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
            throw new RuntimeException(e);
        }
    }
}
