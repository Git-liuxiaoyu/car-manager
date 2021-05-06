package com.woniu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: 小刘
 * @Date: 2021/04/29/10:45
 * @Description:
 */
@SpringBootApplication
@MapperScan("com.woniu.dao")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }


}
