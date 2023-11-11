import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Main3 {
  public static void main(String[] args)
      throws ScriptException, IllegalAccessException, NoSuchMethodException {
    // js引擎和反射执行的demo

    ScriptEngineManager manager = new ScriptEngineManager();
    ScriptEngine engine = manager.getEngineByName("AviatorScript");
    engine.put("a", 2);
    engine.put("b", 5);
//    Object eval = engine.eval("[1,2].includes(a)");


    Object eval = engine.eval("(1+2) * (212-100)");



    System.out.println(eval);
  }
}
