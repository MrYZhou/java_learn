package base;

import java.util.Base64;

public class Encode {
    public static void main(String[] args) {
        String str="hello sdasd as";
        String s = Base64.getEncoder().encodeToString(str.getBytes());
        System.out.println(s);

        new String(Base64.getDecoder().decode(str));
    }
}
