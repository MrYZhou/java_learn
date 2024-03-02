package httpdemo;

import cn.hutool.Hutool;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import tool.HttpTool;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Test1 {
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        String appid = "20221030001425574";
        String salt = "12345678";
        String q ="";
        String secret ="m7EyIihj0nyGMIsziidd";

        String sign = DigestUtil.md5Hex(appid+q+salt+secret);
        map.put("sign",sign);
        map.put("appid",appid);
        map.put("from","zh");
        map.put("to","en");
        map.put("q","你好");
        String jsonStr = JSONUtil.toJsonStr(map);
        // 发起 POST 请求
        String postUrl = "https://fanyi-api.baidu.com/api/trans/vip/translate";
        String postData = jsonStr;

        HttpResponse postResponse = HttpUtil.createPost(postUrl)
                .body(postData)
                .execute();
        String postResponseBody = postResponse.body();
        System.out.println("POST Response: " + postResponseBody);


    }


}
