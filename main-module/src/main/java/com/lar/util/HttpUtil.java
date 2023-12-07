package com.lar.util;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;

import java.util.Map;

public class HttpUtil {
    /**
     * @param path          请求的接口地址
     * @param requestMethod 请求方法 get/post
     * @param requestParams 请求参数
     * @param headerObject  请求头参数
     * @param token         权限
     * @return 服务执行结果
     */
    public static String callHttpRequest(String path, String requestMethod, String requestParams, Map headerObject, String token) {
        HttpRequest httpRequest = HttpRequest.post(path);
        if ("GET".equalsIgnoreCase(requestMethod)) httpRequest = HttpRequest.get(path);
        httpRequest.header(Header.AUTHORIZATION, token)
                .body(requestParams)
                .timeout(20000);
        // 拼接其他请求头
        if (headerObject != null) {
            for (Object key : headerObject.keySet()) {
                httpRequest.header((String) key, String.valueOf(headerObject.get(key)));
            }
        }
        return httpRequest.execute().body();
    }

    /**
     * @param path          请求的接口地址
     * @param requestMethod 请求方法 get/post
     * @param requestParams 请求参数
     * @param headerObject  请求头参数
     * @return 服务执行结果
     */
    public static String callHttpRequest(String path, String requestMethod, String requestParams, Map headerObject) {
        // 此处可以直接设置为项目默认的
        return HttpUtil.callHttpRequest(path, requestMethod, requestParams, headerObject, "");
    }
}
