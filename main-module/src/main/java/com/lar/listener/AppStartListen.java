package com.lar.listener;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnJava;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.system.JavaVersion;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
// 通过classloader,存在类
@ConditionalOnClass
// 通过spring的beanFactory中存在bean
//@ConditionalOnBean
// 在jdk版本生效
@ConditionalOnJava(range = ConditionalOnJava.Range.EQUAL_OR_NEWER, value = JavaVersion.SEVENTEEN)
// 通过配置文件
//@ConditionalOnProperty
// 判断环境是web
@ConditionalOnWebApplication
public class AppStartListen implements ApplicationListener<ContextRefreshedEvent> {


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 当应用启动的时候
    }
}
