package com.pinghua.web.jasypt;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 使用jasypt加密Spring Boot应用中的敏感配置
 * https://blog.csdn.net/myle69/article/details/81052489
 *
 * Created by wenhuaping on 2019/3/29.
 * @author: wenhuaping
 *
 * 在使用jasypt-spring-boot-starter的前提下
 * jasypt版本      springboot版本
 * 2.1.0           2.1.0
 * 1.5             1.4.2
 * 1.5             1.5.3
 * 1.8             1.4.2
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogApplicationTests {

    @Autowired
    StringEncryptor stringEncryptor;

    @Test
    public void test() {
        System.out.println(stringEncryptor.encrypt("zookeeper://47.106.95.116:2181"));
    }

}
