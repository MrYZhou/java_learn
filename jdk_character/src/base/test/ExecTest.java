package base.test;

import base.ExecUtil;

import java.io.IOException;

public class ExecTest {
    public static void main(String[] args) {
        // 单参数写法
//        try {
//            String res = ExecUtil.exec("java --version" , 5);
//            System.out.println(res);
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
//        // 多参数写法
//        try {
//            String res = ExecUtil.exec(new String[]{"ping" , "www.baidu.com"}, 5);
//            System.out.println(res);
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
        // 运行程序
        try {
            String res = ExecUtil.exec("D:\\Users\\JNPF\\Desktop\\project\\py\\code-gen-client\\dist\\o.exe" , 5);
            System.out.println(res);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}
