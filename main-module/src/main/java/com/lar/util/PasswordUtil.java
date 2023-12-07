package com.lar.util;

import cn.dev33.satoken.secure.SaSecureUtil;

public class PasswordUtil {

    public static String encode(String password, String salt) {
        return SaSecureUtil.md5BySalt(password, salt);
    }
}
