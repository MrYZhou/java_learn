package com.lar;

import cn.hutool.json.JSONUtil;
import com.lar.test.spi.Phone;
import com.lar.util.HttpUtil;
import com.lar.util.JsonUtil;
import org.codehaus.groovy.runtime.powerassert.SourceText;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.sql.Driver;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

@SpringBootTest
public class Test1 {

    @Test
    @DisplayName("测试翻译")
    public void test211() throws Exception {

    }


    @Test
    @DisplayName("测试spi2")
    public void test3() throws Exception {
        // 执行Java SPI的规范
        ServiceLoader<Phone> serviceLoader = ServiceLoader.load(Phone.class);
        for (Phone driver : serviceLoader) {
            System.out.println(driver.getClass().getName());
        }
    }
    @Test
    @DisplayName("测试spi")
    public void test2() throws Exception {
        // 执行Java SPI的规范
        ServiceLoader<Driver> serviceLoader = ServiceLoader.load(Driver.class);
        for (Driver driver : serviceLoader) {
            System.out.println(driver.getClass().getName());
        }
    }

    @Test
    public void test1() throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        for (int i = 0; i < 10000 ; i++) {
            System.out.println(1);
        }
        stopWatch.stop();

        System.out.println(stopWatch.getTotalTimeMillis());
    }
}
