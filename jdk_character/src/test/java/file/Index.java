package file;

import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import cn.hutool.json.JSONUtil;
import com.twelvemonkeys.lang.StringUtil;
import lombok.Data;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class Index {
    private static final Object lock2 = new Object();
//    private static volatile char current = 'A';
    private static final ReentrantLock lock = new ReentrantLock();
    private static final Condition conditionA = lock.newCondition();
    private static final Condition conditionB = lock.newCondition();
    private static final Condition conditionC = lock.newCondition();
    private static volatile char current = 'A';

    @Test
    public void ste(){
        String str="64e8cdb0bd9711e4fffd23e925fa51f56e03a45cd524229ba0d1fea0a75c8e1ceb822b97cd65bc646285dbcd368d340495207c1c9016cacb59d78f0d";
        try {
            System.out.println(DesUtil.aesOrDecode(str,false,true));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> judgeReqValid(String id) {
        if(StringUtil.isEmpty(id) ){
            return null;
        }
        String[] split = id.split(",");
        List<String> collect = Arrays.stream(split).collect(Collectors.toList());
        boolean b = judgeReqValid(collect);
        return b ? collect : null;
    }

    public static boolean judgeReqValid(List<String> ids) {

        ids.set(0, "C");
        return true;
    }
    @Data
    class  Student{
        String name;
        List<String> ids;
    }
    private String judgeReqValidg(String id){
        if(id==null) return  null;
        return id;
    }

    private boolean judgeReqValidg(List<String> ids){
        return true;
    }
    public boolean checkParam(Object data){
        ArrayList<String> list = new ArrayList<>();
        list.add("name");
        list.add("ids");
        for (String s : list) {
            Map map = JSONUtil.toBean( JSONUtil.toJsonStr(data), Map.class);
            String value = String.valueOf(map.get(s));
            if(! StringUtil.isEmpty(value)){
                String realValue = judgeReqValidg(value);
                System.out.println(realValue);
//                if(realValue == null || realValue == "null") return false;
            }
        }
        return true;
    }
    @Test
    @DisplayName("测试4")
    public void test4() {
        ArrayList<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        Student student = new Student();
        student.setName("uu");
        student.setIds(list);

        boolean b = checkParam(student);
        System.out.println(b);
    }



    @Test
    @DisplayName("测试文件夹目录生成")
    public void test() {
        String path = "D:\\Users\\JNPF\\Desktop\\project\\java_learn\\jdk_character\\src\\test\\java\\file\\001.docx";
        File file = new File(path);

        // 示例用法
        FileCryptoUtil.encryptFile(file);
        FileCryptoUtil.decryptFile(file);

    }

    @Test
    @DisplayName("测试加密")
    public void test2() throws NoSuchAlgorithmException {
        String customKeyStr = "fsafasgfdgfddsadsadsadas";
        // UTF-8编码后截取前32字节（强制对齐256bits）
        byte[] keyBytes = Arrays.copyOf(customKeyStr.getBytes(StandardCharsets.UTF_8), 32);
        System.out.println(keyBytes);
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, keyBytes);
    }

    @Test
    @DisplayName("测试")
    public void test3() {
        ArrayList<String> list = new ArrayList<>();
        list.add("A");
        list.set(0, "B");
//        System.out.println(list.get(0));
        String id = "A";
        List<String> strings = judgeReqValid(id);
        System.out.println(strings.get(0));
    }


}
