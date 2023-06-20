import org.junit.jupiter.api.Test;

public class MatchTest {
    @Test
    public void test() {
        String a = """
                121""";
        String name = "xiao";
        int ret = switch (name) {
            case "ai" -> 1;
            case "xiao", "xian" -> 2;
            default -> 0;
        };
        System.out.println(ret);
    }
}
