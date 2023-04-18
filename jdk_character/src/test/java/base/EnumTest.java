package base;

public class EnumTest {
    public static void main(String[] args) {

        String nn = "123flowMonitor32";
        System.out.println(nn.split("flowMonitor")[0]);
        System.out.println(nn.split("flowMonitor")[1]);
        Status status = Status.of(1);
        switch(status){
            case StormyStatusFirst:
                System.out.println(1);
                break;
            case StormyStatusSecond:
                System.out.println(2);
                break;
            default: break;
        }
    }
}
