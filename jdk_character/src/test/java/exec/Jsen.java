package exec;

import org.junit.jupiter.api.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.StringJoiner;

public class Jsen {
    @Test
    public  void test1(){
        StringJoiner stringJoiner2= new StringJoiner(",");
        stringJoiner2.add("test");
        stringJoiner2.add("test2");

        System.out.println( stringJoiner2);
        String valueData = "test,test2";

    }
}
