package com.bluesgao.throttlerserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class ThrottlerServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThrottlerServerApplication.class, args);
    }

}
