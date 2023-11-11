package common.enumdemo;


import org.junit.jupiter.api.Test;

import static common.enumdemo.StatusInfo.StormyStatusFirst;
import static common.enumdemo.StatusInfo.StormyStatusSecond;

public class EnumTest {
    @Test
    public void testEnum() {
        Status status = Status.of("暴雨一");
        System.out.println(Status.of("暴雨一").name);
        switch (status) {
            case StormyStatusFirst:
                System.out.println(status.name);
                System.out.println(status.value);
                break;
            case StormyStatusSecond:
                System.out.println(2);
                break;
            default:
                break;
        }
    }

    @Test
    public void testInterfaceValue() {

        String status = "1";
        this.ju(status);
    }

    public void ju(String status) {
        switch (status) {
            case StormyStatusFirst:
                System.out.println(112);
                break;
            case StormyStatusSecond:
                System.out.println(1123);
                System.out.println(2);
                break;
            default:
                break;
        }
    }
}
