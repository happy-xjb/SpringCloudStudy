package com.atguigu.springcloud.cfgbeans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ConfigBean //boot -->spring applicationContext.xml -- @Configuration配置 = applicationContext.xml
{
    @Bean
    public RestTemplate getRestTemplate()
    {
        return new RestTemplate();
    }
}
//applicationContext.xml == ConfigBean(@Configuration)
//<bean id = "userService" class="com.atguigu.tmall.userServiceImpl">
