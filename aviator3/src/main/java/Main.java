import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorNumber;
import com.googlecode.aviator.runtime.type.AviatorObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
  public static void main(String[] args) {
    // 注册函数
    AviatorEvaluator.addFunction(new LikeFunction());
    AviatorEvaluator.addFunction(new AddFunction());
    AviatorEvaluator.addFunction(new ContainFunction());

    HashMap<String, Object> map = new HashMap<>();
    map.put("a","hello");
    map.put("b","llo");
    System.out.println(AviatorEvaluator.execute("like(a,b)",map));

    HashMap<String, Object> map2 = new HashMap<>();
    map2.put("a",new ArrayList<String>(){{add("llo");add("ee");}});
    map2.put("b","llo1");

    System.out.println(AviatorEvaluator.execute("contain(a,b)",map2));

    System.out.println(AviatorEvaluator.execute("add(1, 2)")); // 3.0
    System.out.println(AviatorEvaluator.execute("add(add(1, 2), 100)")); // 103.0
  }
}

class AddFunction extends AbstractFunction {
  @Override
  public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2) {
    Number left = FunctionUtils.getNumberValue(arg1, env);
    Number right = FunctionUtils.getNumberValue(arg2, env);
    return new AviatorDouble(left.doubleValue() + right.doubleValue());
  }
  @Override
  public String getName() {
    return "add";
  }


}

// like
class LikeFunction extends AbstractFunction {
  @Override
  public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2) {
    String left = FunctionUtils.getStringValue(arg1, env);
    String right = FunctionUtils.getStringValue(arg2, env);
    return new AviatorDouble(left.indexOf(right));
  }

  @Override
  public String getName() {
    return "like";
  }
}

class ContainFunction extends AbstractFunction {
  @Override
  public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2) {
    List<String> list = (List<String>) FunctionUtils.getJavaObject(arg1,env);
    String right = FunctionUtils.getStringValue(arg2, env);


    Boolean contains = list.contains(right);
    Integer count = contains==true?1:-1;
    return new AviatorDouble(count);
  }

  @Override
  public String getName() {
    return "contain";
  }
}



